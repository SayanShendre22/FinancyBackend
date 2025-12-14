package com.app.DTO;

import java.time.Instant;
import java.time.LocalDateTime;

public class RedeemResponseDTO {
	private Long id;
	private Long coins;
	private String type;
	private String status;
	private Instant createdAt;
	private Long walletId;
	
	@Override
	public String toString() {
		return "RedeemResponseDTO [id=" + id + ", coins=" + coins + ", type=" + type + ", status=" + status
				+ ", createdAt=" + createdAt + ", walletId=" + walletId + "]";
	}

	public RedeemResponseDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant instant) {
		this.createdAt = instant;
	}

	public Long getWalletId() {
		return walletId;
	}

	public void setWalletId(Long walletId) {
		this.walletId = walletId;
	}
	
	
	
	
	
}
