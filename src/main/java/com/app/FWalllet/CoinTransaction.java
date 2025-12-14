package com.app.FWalllet;

import java.time.LocalDateTime;

import com.app.user.UserData;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;

@Entity
public class CoinTransaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int tid;
	private Long coins = 0L; // default 0
	private String type;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fid", referencedColumnName = "wid")
	@JsonIgnore
	private FWallet fwallet;

	private LocalDateTime createdAt;
	
	@Override
	public String toString() {
		return "CoinTransaction [tid=" + tid + ", coins=" + coins + ", type=" + type + ", fwallet=" + fwallet
				+ ", createdAt=" + createdAt + "]";
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	public int getTid() {
		return tid;
	}

	public void setTid(int tid) {
		this.tid = tid;
	}

	public Long getCoins() {
		return coins;
	}

	public void setCoins(Long coins) {
		this.coins = coins;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FWallet getFwallet() {
		return fwallet;
	}

	public void setFwallet(FWallet fwallet) {
		this.fwallet = fwallet;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public CoinTransaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CoinTransaction(int tid, Long coins, String type, FWallet fwallet) {
		this.tid = tid;
		this.coins = coins;
		this.type = type;
		this.fwallet = fwallet;
	}

	public CoinTransaction(Long coins, String type, FWallet fwallet) {
		this.coins = coins;
		this.type = type;
		this.fwallet = fwallet;
	}
	
	
}
