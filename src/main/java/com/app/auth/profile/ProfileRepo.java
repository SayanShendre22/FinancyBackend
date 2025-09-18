package com.app.auth.profile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.user.UserData;

@Repository
public interface ProfileRepo extends JpaRepository<ProfileModel, Integer> {
	
	ProfileModel findByUserId(long uid);

	
}
