package com.app.balanceAcc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.JwtService;
import com.app.user.UserData;
import com.app.user.UserRepo;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/bank")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BalanceController {

	@Autowired
	BalanceService balanceService;

	@Autowired
	JwtService jwt;

	@Autowired
	UserRepo ur;

	// create account
	@PostMapping("/createBankAccount")
	public BalanceModel createAccount(@RequestBody BalanceModel bm, @RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		UserData userDetails = ur.getById(jwt.extractUserId(token));
		return balanceService.createAccount(bm, userDetails);
	}

	// update account
	@PostMapping("/updateBankAccount")
	public BalanceModel updateAccount(@RequestBody BalanceModel bm) {
		return balanceService.updateAccount(bm);
	}

	// delete account
	@RequestMapping("/deleteAcount/{id}")
	public boolean deleteAccount(@PathVariable long id) {
		return balanceService.deleteAccount(id);
	}

	// read account
	@RequestMapping("/getAccount")
	public ResponseEntity<List<BalanceModel>> getAccount(HttpSession session,
			@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		UserData user = ur.getById(jwt.extractUserId(token));
		if (user != null) {
			return new ResponseEntity<List<BalanceModel>>(balanceService.getAccountDetails(user), HttpStatus.OK);
		} else {
			System.out.println("unable to find account details");
			return new ResponseEntity<List<BalanceModel>>(balanceService.getAccountDetails(user), HttpStatus.NOT_FOUND);
		}
	}

	// update balance
	@PostMapping("/updateBalance/{bal}")
	public boolean updateBalance(@PathVariable long bal, @RequestHeader("Authorization") String authHeader) {
		System.out.println("finding user ");
		String token = authHeader.replace("Bearer ", "");
		UserData user = ur.getById(jwt.extractUserId(token));
		if (user != null) {
			System.out.println("found user");
			return balanceService.upadateBalance(user.getId(), bal);
		} else {
			System.out.println("unable to upadate balance");
			return false;
		}
	}

}
