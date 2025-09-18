package com.app.auth.profile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.user.UserData;

@Service
public class ProfileService {
	
//	@Value(value = "${project.image}")
	private String path = "src/main/resources/static/images/profilePic/";


	@Autowired
	ProfileRepo profileRepo;

	public ProfileModel SaveProfile(ProfileModel profile,UserData user) throws IOException {
		
		profile.setUser(user);
		
		ProfileModel pnew = profileRepo.findByUserId(user.getId());
//		System.out.println("undder profile service "+pnew+" "+user);
		ProfileModel p;
		
		//update profile
		if(pnew!=null) {
			System.out.println("updating profile" + user.toString());
			String oldFileName = pnew.getProfilePic();
			
			pnew.setAddress(profile.getAddress());
			pnew.setDob(profile.getDob());
			pnew.setJob(profile.getJob());
			pnew.setMobileNo(profile.getMobileNo());
			pnew.setPP(profile.getPP());
			pnew.setProfilePic(profile.getProfilePic());
			pnew.setSalary(profile.getSalary());
			pnew.setFullname(profile.getFullname());
			pnew.setGoal(profile.getGoal());
			
			
			//file name
			String filename = pnew.getPP().getOriginalFilename();
			//full path
			
			String randomId = UUID.randomUUID().toString();
			String fileName1 = randomId+filename;
			String filePath=path+File.separator+fileName1;
			
			//create folder if not created
			File F = new File(path);
			if(!F.exists()) {
				F.mkdir();
			}

			//copy file
			Files.copy(pnew.getPP().getInputStream(), Paths.get(filePath));
			
			
			//deleting old profile pic
			
			    try {
			    	Path root = Paths.get(path);
			        Path oldFile = root.resolve(oldFileName);
			        Files.deleteIfExists(oldFile);
			    } catch (IOException e) {
			        throw new RuntimeException("Error: " + e.getMessage());
			    }
			
			
			
			 
			pnew.setProfilePic(fileName1);
			
			p = profileRepo.save(pnew);
			
		}else {  
			// save a new profile
			System.out.println("profile saving");
			//file name
			String filename = profile.getPP().getOriginalFilename();
			
			//full path
			String randomId = UUID.randomUUID().toString();
			String fileName1 = randomId+filename;
			String filePath=path+File.separator+fileName1;
			
			//create folder if not created
			File F = new File(path);
			if(!F.exists()) {
				F.mkdir();
			}
			System.out.println("clear here");
			//copy file
			Files.copy(profile.getPP().getInputStream(), Paths.get(filePath));
			 
			profile.setProfilePic(fileName1);
			
			 p = profileRepo.save(profile);
		}
		
		
		
		if(p!=null) {
			return p;
		}else {
			return null;
		}
	}
	
	// get profile
	public ProfileModel getProfile(UserData u) {
		return profileRepo.findByUserId(u.getId());
	}
	
	
}
