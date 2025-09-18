package com.app.transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.app.balanceAcc.BalanceModel;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Transient;

@Entity
public class TransactionModel {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String type; // "CREDIT" or "DEBIT"

    private String description;
    
    private String category;

    private LocalDateTime timestamp;
    
    @PrePersist
    public void prePersist() {
        this.timestamp = LocalDateTime.now();
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bank_account_id")
    private BalanceModel bankAccount;
	
    private String reciptName;
	
	@Transient
	private MultipartFile recipt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public BalanceModel getBankAccount() {
		return bankAccount;
	}

	public void setBankAccount(BalanceModel bankAccount) {
		this.bankAccount = bankAccount;
	}

	public String getReciptName() {
		return reciptName;
	}

	public void setReciptName(String reciptName) {
		this.reciptName = reciptName;
	}

	public MultipartFile getRecipt() {
		return recipt;
	}

	public void setRecipt(MultipartFile recipt) {
		this.recipt = recipt;
	}

	public TransactionModel(Long id, BigDecimal amount, String type, String description, String category,
			LocalDateTime timestamp, BalanceModel bankAccount, String reciptName, MultipartFile recipt) {
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.description = description;
		this.category = category;
		this.timestamp = timestamp;
		this.bankAccount = bankAccount;
		this.reciptName = reciptName;
		this.recipt = recipt;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public TransactionModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
