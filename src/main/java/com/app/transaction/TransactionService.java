package com.app.transaction;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.balanceAcc.BalanceModel;
import com.app.balanceAcc.BalanceRepo;

@Service
public class TransactionService {

	private String path = "images/recipts";

	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private BalanceRepo bankRepo;

	// add
	public TransactionModel addTransaction(Long accountId, TransactionModel txn) throws IOException {
		BalanceModel account = bankRepo.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Bank Account not found"));
		txn.setBankAccount(account);

		String filename = txn.getRecipt().getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String filename1 = randomId + filename;
		String filePath = path + File.separator + filename1;

		// create folder if not created
		File F = new File(path);
		if (!F.exists()) {
			F.mkdir();
		}

		// copy file
		Files.copy(txn.getRecipt().getInputStream(), Paths.get(filePath));

		txn.setReciptName(filename1);

		// update bank balance "CREDIT" or "DEBIT"
		// for credit
		if (txn.getType().equalsIgnoreCase("CREDIT")) {
			account.setBalance(account.getBalance() + txn.getAmount().floatValue());
		} else {
			// for DEBIT
			account.setBalance(account.getBalance() - txn.getAmount().floatValue());
		}

		return transactionRepo.save(txn);
	}

	// read
	public List<TransactionModel> getTransactionsByAccountId(Long accountId) {
		return transactionRepo.findByBankAccountId(accountId);
	}

	// delete
	public boolean deleteTransaction(long id) {

		TransactionModel tm = transactionRepo.getById(id);

		// update bank balance "CREDIT" or "DEBIT"
		// for credit
		if (tm.getType().equalsIgnoreCase("CREDIT")) {
			tm.getBankAccount().setBalance(tm.getBankAccount().getBalance() - tm.getAmount().floatValue());
		} else {
			// for DEBIT
			tm.getBankAccount().setBalance(tm.getBankAccount().getBalance() + tm.getAmount().floatValue());
		}
		bankRepo.save(tm.getBankAccount());
		transactionRepo.deleteById(id);
		if (transactionRepo.findById(id) != null) {
			return false;
		} else {
			return true;
		}

	}

	// update
	public TransactionModel updateTransaction(Long accountId, TransactionModel txn, long tid) throws IOException {
		BalanceModel account = bankRepo.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Bank Account not found"));
		txn.setBankAccount(account);

		String filename = txn.getRecipt().getOriginalFilename();
		String randomId = UUID.randomUUID().toString();
		String filename1 = randomId + filename;
		String filePath = path + File.separator + filename1;

		TransactionModel tm = transactionRepo.findById(tid).get();

		// create folder if not created
		File F = new File(path);
		if (!F.exists()) {
			F.mkdir();
		}

		// copy file
		Files.copy(txn.getRecipt().getInputStream(), Paths.get(filePath));

		// deleting old profile pic

		try {
			Path root = Paths.get(path);
			Path oldFile = root.resolve(tm.getReciptName());
			Files.deleteIfExists(oldFile);
		} catch (IOException e) {
			throw new RuntimeException("Error: " + e.getMessage());
		}

		// update bank balance "CREDIT" or "DEBIT"
		// bring to the normal
		if (tm.getType().equalsIgnoreCase("CREDIT")) {
			account.setBalance(account.getBalance() - tm.getAmount().floatValue());
		} else {
			// for DEBIT
			account.setBalance(account.getBalance() + tm.getAmount().floatValue());
		}

		// for credit
		// updating new
		if (txn.getType().equalsIgnoreCase("CREDIT")) {
			account.setBalance(account.getBalance() + txn.getAmount().floatValue());
		} else {
			// for DEBIT
			account.setBalance(account.getBalance() - txn.getAmount().floatValue());
		}

		tm.setReciptName(filename1);
		tm.setAmount(txn.getAmount());
		tm.setBankAccount(txn.getBankAccount());
		tm.setDescription(txn.getDescription());
		tm.setRecipt(txn.getRecipt());
		tm.setType(txn.getType());
		tm.setCategory(txn.getCategory());

		return transactionRepo.save(tm);
	}

	// get txn by category
	public List<TransactionModel> getTransationByCat(String cat) {
		return transactionRepo.findByCategory(cat);
	}

}
