package com.app.DTO;

public class RedeemReqDTO {
	public Long walletId;
	public Long coins;
	public String type;
	public String details;
	
	@Override
	public String toString() {
		return "RedeemReqDTO [walletId=" + walletId + ", coins=" + coins + ", type=" + type + ", details=" + details
				+ "]";
	}
}
