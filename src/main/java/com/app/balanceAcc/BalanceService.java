package com.app.balanceAcc;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.user.UserData;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class BalanceService {
	
	@Autowired
	BalanceRepo balanceRepo;
	
	//create new account 
	public BalanceModel createAccount(BalanceModel b,UserData user ) {
		b.setUser(user);
		return balanceRepo.save(b);
	}
	
	//delete account
	public boolean deleteAccount(long id) {
		balanceRepo.deleteById(id);
		try {
			BalanceModel b = balanceRepo.findById(id).get();
			return false;
		}catch (Exception e) {
			return true;
		}
	}
	
	// update Account 
	public BalanceModel updateAccount(BalanceModel bal) {
		
		BalanceModel b = balanceRepo.findById(bal.getId()).get();
		b.setAccountNumber(bal.getAccountNumber());
		b.setAccountType(bal.getAccountType());
		b.setBalance(bal.getBalance());
		b.setBankName(bal.getBankName());
		b.setFetchedAt(LocalDateTime.now());
		b.setIfscCode(bal.getIfscCode());
		
		return balanceRepo.save(b);
	}
	
	// read account 
	public List<BalanceModel> getAccountDetails(UserData user) {
		return balanceRepo.findByUser(user);
	}
	
	//update balance
	public boolean upadateBalance(long id,double bal) {
		try {
			BalanceModel b = balanceRepo.findById(id).get();
			b.setBalance(bal);
			BalanceModel bnew = balanceRepo.save(b);
			if(bnew!=null) {
				return true;
			}else {
				return false;
			}
			
		}catch (Exception e) {
			return false;
		}
	}
	
	

}
