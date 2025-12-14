package com.app.FWalllet.Redeem;

public interface RedeemStrategy {

	void validate(RedeemRequest req);

	void process(RedeemRequest req) throws Exception; // might call external gateways

}
