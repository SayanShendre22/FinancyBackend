package com.app.balanceAcc;

import java.time.LocalDateTime;
import java.util.List;

import com.app.transaction.TransactionModel;
import com.app.user.UserData;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class BalanceModel {
	
	 	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String bankName;

	    private String accountNumber;

	    private Double balance;
	    
	    private String ifscCode;
	    
	    private String accountType;

	    private LocalDateTime fetchedAt;

	    @JsonIgnore
	    @ManyToOne(fetch = FetchType.LAZY)
	    @JoinColumn(name = "user_id")
//	    @JsonBackReference   // ✅ breaks recursion  
	    private UserData user;
	    
	    @JsonIgnore
	    @OneToMany(mappedBy = "bankAccount", cascade = CascadeType.ALL)
	    private List<TransactionModel> transactions;


		public BalanceModel(Long id, String bankName, String accountNumber, Double balance, String ifscCode,
				String accountType, LocalDateTime fetchedAt, UserData user, List<TransactionModel> transactions) {
			this.id = id;
			this.bankName = bankName;
			this.accountNumber = accountNumber;
			this.balance = balance;
			this.ifscCode = ifscCode;
			this.accountType = accountType;
			this.fetchedAt = fetchedAt;
			this.user = user;
			this.transactions = transactions;
		}

		public List<TransactionModel> getTransactions() {
			return transactions;
		}

		public void setTransactions(List<TransactionModel> transactions) {
			this.transactions = transactions;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getBankName() {
			return bankName;
		}

		public void setBankName(String bankName) {
			this.bankName = bankName;
		}

		public String getAccountNumber() {
			return accountNumber;
		}

		public void setAccountNumber(String accountNumber) {
			this.accountNumber = accountNumber;
		}

		public Double getBalance() {
			return balance;
		}

		public void setBalance(Double balance) {
			this.balance = balance;
		}

		public String getIfscCode() {
			return ifscCode;
		}

		public void setIfscCode(String ifscCode) {
			this.ifscCode = ifscCode;
		}

		public String getAccountType() {
			return accountType;
		}

		public void setAccountType(String accountType) {
			this.accountType = accountType;
		}

		public LocalDateTime getFetchedAt() {
			return fetchedAt;
		}

		public void setFetchedAt(LocalDateTime fetchedAt) {
			this.fetchedAt = fetchedAt;
		}

		public UserData getUser() {
			return user;
		}

		public void setUser(UserData user) {
			this.user = user;
		}

		public BalanceModel(Long id, String bankName, String accountNumber, Double balance, String ifscCode,
				String accountType, LocalDateTime fetchedAt, UserData user) {
			this.id = id;
			this.bankName = bankName;
			this.accountNumber = accountNumber;
			this.balance = balance;
			this.ifscCode = ifscCode;
			this.accountType = accountType;
			this.fetchedAt = fetchedAt;
			this.user = user;
		}

		public BalanceModel() {
		}
	    
	    
	    

}
