package com.app.transaction;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.balanceAcc.BalanceModel;
import com.app.balanceAcc.BalanceRepo;
import com.app.user.UserRepo;

@Service
public class TransactionService {

	private String path = "src/main/resources/static/images/recipts/";

	@Autowired
	private TransactionRepository transactionRepo;

	@Autowired
	private BalanceRepo bankRepo;

	@Autowired
	private UserRepo ur;

	// add
	public TransactionModel addTransaction(Long accountId, TransactionModel txn) throws IOException {

		BalanceModel account = bankRepo.findById(accountId)
				.orElseThrow(() -> new RuntimeException("Bank Account not found"));
		txn.setBankAccount(account);

		if (txn.getRecipt() != null && !txn.getRecipt().isEmpty()) {
			String filename = txn.getRecipt().getOriginalFilename();
			String randomId = UUID.randomUUID().toString();
			String filename1 = randomId + filename;
			String filePath = path + File.separator + filename1;

			// create folder if not created
			File F = new File(path);
			if (!F.exists()) {
				F.mkdir();
			}
			System.out.println("clear here" + txn.getRecipt().getInputStream() + " : " + Paths.get(filePath));
			// copy file
			Files.copy(txn.getRecipt().getInputStream(), Paths.get(filePath));

			txn.setReciptName(filename1);
		}

		// update bank balance "CREDIT" or "DEBIT"
		// for credit
		if (txn.getType().equalsIgnoreCase("income")) {
			account.setBalance(account.getBalance() + txn.getAmount().floatValue());
		} else {
			// for DEBIT
			account.setBalance(account.getBalance() - txn.getAmount().floatValue());
		}
		System.out.println("saving tnx");
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
		if (tm.getType().equalsIgnoreCase("income")) {
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

	public List<TransactionModel> getAllTransactionByUser(long uid) {
		List<TransactionModel> allTransactions = new ArrayList<>();
		List<BalanceModel> allBalanceModels = bankRepo.findByUser(ur.getById(uid));

		for (BalanceModel b : allBalanceModels) {
			allTransactions.addAll(transactionRepo.findByBankAccountId(b.getId()));
		}
		return allTransactions.stream().sorted(Comparator.comparing(TransactionModel::getTimestamp).reversed())
				.toList();
	}

	public List<TransactionModel> getWeeklyByUser(long uid) {
		List<TransactionModel> allTransactions = getAllTransactionByUser(uid);
		List<TransactionModel> weeklyTransactions = new ArrayList<>();
		for (TransactionModel t : allTransactions) {
			LocalDateTime oneWeekAgo = LocalDateTime.now().minusWeeks(1);
			if (t.getTimestamp().isAfter(oneWeekAgo)) {
				weeklyTransactions.add(t);
			}
		}
		return weeklyTransactions.stream().sorted(Comparator.comparing(TransactionModel::getTimestamp).reversed())
				.toList();
	}

	public List<TransactionModel> getMonthlyByUser(long uid) {
		List<TransactionModel> allTransactions = getAllTransactionByUser(uid);
		List<TransactionModel> monthlyTransactions = new ArrayList<>();
		for (TransactionModel t : allTransactions) {
			LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
			if (t.getTimestamp().isAfter(oneMonthAgo)) {
				monthlyTransactions.add(t);
			}
		}
		return monthlyTransactions.stream().sorted(Comparator.comparing(TransactionModel::getTimestamp).reversed())
				.toList();
	}

	public List<TransactionModel> getYearlyByUser(long uid) {
		List<TransactionModel> allTransactions = getAllTransactionByUser(uid);
		List<TransactionModel> yerlyTransactions = new ArrayList<>();
		for (TransactionModel t : allTransactions) {
			LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1);
			if (t.getTimestamp().isAfter(oneYearAgo)) {
				yerlyTransactions.add(t);
			}
		}
		return yerlyTransactions.stream().sorted(Comparator.comparing(TransactionModel::getTimestamp).reversed())
				.toList();
	}

}
