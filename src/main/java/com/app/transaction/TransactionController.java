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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/accounts/{accountId}/transactions")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping
	public ResponseEntity<TransactionModel> addTransaction(@PathVariable Long accountId, @ModelAttribute TransactionModel txn) throws IOException {
		TransactionModel saved = transactionService.addTransaction(accountId, txn);
		return new ResponseEntity<>(saved, HttpStatus.CREATED);
	}

	@GetMapping
	public List<TransactionModel> getTransactions(@PathVariable Long accountId) {
		return transactionService.getTransactionsByAccountId(accountId);
	}

}
