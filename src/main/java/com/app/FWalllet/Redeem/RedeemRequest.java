package com.app.FWalllet.Redeem;

import java.time.Instant;

import com.app.FWalllet.FWallet;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class RedeemRequest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wallet_id")
	private FWallet wallet;


	private Long coins;
	private String type; // upi, bank, gift
	private String details; // json/text for method details


	@Enumerated(EnumType.STRING)
	private Status status = Status.PENDING;


	private Instant createdAt = Instant.now();


	public enum Status { PENDING, APPROVED, REJECTED, FAILED}
	
	
	// getters/setters
	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public FWallet getWallet() { return wallet; }
	public void setWallet(FWallet wallet) { this.wallet = wallet; }
	public Long getCoins() { return coins; }
	public void setCoins(Long coins) { this.coins = coins; }
	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
	public String getDetails() { return details; }
	public void setDetails(String details) { this.details = details; }
	public Status getStatus() { return status; }
	public void setStatus(Status status) { this.status = status; }
	public Instant getCreatedAt() { return createdAt; }
	public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

}
