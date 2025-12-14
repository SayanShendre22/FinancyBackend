package com.app.FWalllet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.balanceAcc.BalanceModel;
import com.app.security.JwtService;
import com.app.user.UserData;
import com.app.user.UserRepo;

@RestController
@RequestMapping("/fwallet")
@CrossOrigin(origins = "http://localhost:8082")
public class FWalletController {

	@Autowired
	JwtService jwt;

	@Autowired
	UserRepo ur;

	@Autowired
	private FWalletService walletService;
	
	@Autowired
	private FWalletRepo fwr;

	// create wallet
	@PostMapping("/createFWallet")
	public FWallet createFWallet(@RequestHeader("Authorization") String authHeader) {
		System.out.println("createWallets inside ");
		String token = authHeader.replace("Bearer ", "");
		UserData userDetails = ur.getById(jwt.extractUserId(token));
		return walletService.createWallet(userDetails);
	}
	
	// update account
	@PostMapping("/updateWallet/{type}/{amount}")
	public ResponseEntity<FWallet> updateAccount(@PathVariable long amount,@PathVariable String type, @RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		UserData userDetails = ur.getById(jwt.extractUserId(token));
		return walletService.updateBalance(amount, fwr.findByUser(userDetails), type);
	}
	
	@GetMapping
	public FWallet getWallet(@RequestHeader("Authorization") String authHeader) {
//		System.out.println("here inside getWallet");
		String token = authHeader.replace("Bearer ", "");
		UserData userDetails = ur.getById(jwt.extractUserId(token));
		return walletService.getWallet(userDetails);
	}

}
