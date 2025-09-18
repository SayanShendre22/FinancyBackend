package com.app.InvestmentNsaving;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.user.UserData;

@Repository
public interface InvestMentRepo extends JpaRepository<InvestmentModel, Long> {
	
//	List<InvestmentModel> findByUser(UserData user);

}
