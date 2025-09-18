package com.app.auth.profile;

import org.springframework.web.multipart.MultipartFile;

import com.app.user.UserData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Transient;

@Entity
public class ProfileModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	int pId;
	private String fullname;
	private String address;
	private String job;
	private String salary;
	private String mobileNo;
	private String dob;
	private String profilePic;
	private String goal; 
	
	@Transient
	private MultipartFile PP;
	
	@OneToOne(cascade = CascadeType.MERGE,fetch = FetchType.LAZY)
	@JoinColumn(name = "uid", referencedColumnName = "id")
	private UserData user;

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public int getpId() {
		return pId;
	}

	@Override
	public String toString() {
		return "ProfileModel [pId=" + pId + ", fullname=" + fullname + ", address=" + address + ", job=" + job
				+ ", salary=" + salary + ", mobileNo=" + mobileNo + ", dob=" + dob + ", profilePic=" + profilePic
				+ ", goal=" + goal + ", PP=" + PP + ", user=" + user + "]";
	}

	public void setpId(int pId) {
		this.pId = pId;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public MultipartFile getPP() {
		return PP;
	}

	public void setPP(MultipartFile pP) {
		PP = pP;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}
	
	public ProfileModel(int pId, String fullname, String address, String job, String salary, String mobileNo,
			String dob, String profilePic, String goal, MultipartFile pP, UserData user) {
		this.pId = pId;
		this.fullname = fullname;
		this.address = address;
		this.job = job;
		this.salary = salary;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.profilePic = profilePic;
		this.goal = goal;
		PP = pP;
		this.user = user;
	}


	public ProfileModel(int pId, String address, String job, String salary, String mobileNo, String dob,
			String profilePic, MultipartFile pP, UserData user) {
		this.pId = pId;
		this.address = address;
		this.job = job;
		this.salary = salary;
		this.mobileNo = mobileNo;
		this.dob = dob;
		this.profilePic = profilePic;
		PP = pP;
		this.user = user;
	}

	public ProfileModel() {
	}
	


}
