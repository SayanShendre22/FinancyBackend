package com.app.transaction;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.JwtService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

	@Autowired
	JwtService jwt;

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/addTransactions/{accountId}")
	public ResponseEntity<TransactionModel> addTransaction(@PathVariable Long accountId,
			@ModelAttribute TransactionModel txn) throws IOException {
		TransactionModel saved = transactionService.addTransaction(accountId, txn);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping("/getTransactions/{accountId}")
	public List<TransactionModel> getTransactions(@PathVariable Long accountId) {
		return transactionService.getTransactionsByAccountId(accountId);
	}

	@GetMapping("/getAllByUser")
	public List<TransactionModel> getAllTransactionByUser(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		long uid = jwt.extractUserId(token);

		return transactionService.getAllTransactionByUser(uid);

	}

	@PostMapping("/deleteTransaction/{id}")
	public boolean deleteTransaction(@PathVariable long id) {
		return transactionService.deleteTransaction(id);
	}

	@PostMapping("/getWeeklyTnx")
	public List<TransactionModel> getWeekly(@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		long uid = jwt.extractUserId(token);
		return transactionService.getWeeklyByUser(uid);
	}
	
	@PostMapping("/getMonthlyTnx")
	public List<TransactionModel> getMonthly(@RequestHeader("Authorization") String authHeader) {
		System.out.println("/getMonthlyTnx api");
		String token = authHeader.replace("Bearer ", "");
		long uid = jwt.extractUserId(token);
		return transactionService.getMonthlyByUser(uid);
	}
	
	@PostMapping("/getYearlyTnx")
	public List<TransactionModel> getYearly(@RequestHeader("Authorization") String authHeader) {
		System.out.println("/getYearlyTnx api");
		String token = authHeader.replace("Bearer ", "");
		long uid = jwt.extractUserId(token);
		return transactionService.getYearlyByUser(uid);
	}

}
