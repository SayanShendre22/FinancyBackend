package com.app.FWalllet.Redeem;

import org.springframework.stereotype.Component;

@Component("bank")
public class BankStrategy implements RedeemStrategy{
	@Override
	public void validate(RedeemRequest req) {
	if (req.getDetails() == null || req.getDetails().trim().length() < 5)
	throw new IllegalArgumentException("Invalid bank details");
	}


	@Override
	public void process(RedeemRequest req) throws Exception {
	// call ACH/payout
	req.setStatus(RedeemRequest.Status.APPROVED);
	}
}
