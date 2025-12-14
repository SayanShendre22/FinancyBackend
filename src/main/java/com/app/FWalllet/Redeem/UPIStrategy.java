package com.app.FWalllet.Redeem;

import org.springframework.stereotype.Component;

@Component("upi")
public class UPIStrategy implements RedeemStrategy{

	@Override
	public void validate(RedeemRequest req) {
	if (req.getDetails() == null || req.getDetails().trim().length() < 3)
	throw new IllegalArgumentException("Invalid UPI details");
	}


	@Override
	public void process(RedeemRequest req) throws Exception {
	// In real app: call payout gateway
		System.out.println("Need to go payment sayan");
	// for demo: mark approved
	req.setStatus(RedeemRequest.Status.APPROVED);
	}
	
}
