package com.app.FWalllet.Redeem;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.ApplicationContext;

import com.app.DTO.RedeemReqDTO;
import com.app.FWalllet.CoinTransRepo;
import com.app.FWalllet.CoinTransaction;
import com.app.FWalllet.FWallet;
import com.app.FWalllet.FWalletRepo;

@Service
public class RedeemService {

	@Autowired
	private FWalletRepo walletRepo;

	@Autowired
	private RedeemRepo redeemRepo;

	@Autowired
	private CoinTransRepo transRepo;

	@Autowired
	private ApplicationContext ctx; // to fetch strategy by bean name

	private static final long MIN_REDEEM = 10;

	@Transactional
	public RedeemRequest createRedeem(RedeemReqDTO dto) {
		System.out.println("inside createRedeem " + dto.toString());
		FWallet wallet = walletRepo.findById(dto.walletId)
				.orElseThrow(() -> new IllegalArgumentException("Wallet not found"));

		if (dto.coins == null || dto.coins <= 0)
			throw new IllegalArgumentException("Invalid coins");
		if (dto.coins > wallet.getCoins())
			throw new IllegalArgumentException("Insufficient coins");
		if (dto.coins < MIN_REDEEM)
			throw new IllegalArgumentException("Minimum redeem is " + MIN_REDEEM);

		// deduct coins
		wallet.setCoins(wallet.getCoins() - dto.coins);
		walletRepo.save(wallet);

		// create a transaction record marking payout
		transRepo.save(new CoinTransaction(-dto.coins, "redeem_payout",wallet ));

		RedeemRequest req = new RedeemRequest();
		req.setWallet(wallet);
		req.setCoins(dto.coins);
		req.setType(dto.type);
		req.setDetails(dto.details);
		req.setStatus(RedeemRequest.Status.PENDING);

		redeemRepo.save(req);

		// optionally create a transaction record for audit (type=redeem_request)
//		transRepo.save(new CoinTransaction(-dto.coins, "redeem_request", wallet));

		return req;
	}

	@Transactional
	public RedeemRequest processRedeem(Long id) throws Exception {
		RedeemRequest req = redeemRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Redeem not found"));
		if (req.getStatus() != RedeemRequest.Status.PENDING)
			return req;

		String strategyBean = req.getType(); // bean name equals type
		Map<String, RedeemStrategy> map = ctx.getBeansOfType(RedeemStrategy.class);
		RedeemStrategy strategy = map.get(strategyBean);
		if (strategy == null)
			throw new IllegalStateException("No strategy for " + strategyBean);

		// validate
		strategy.validate(req);

		// process (external call) - might throw
		strategy.process(req);

		if (req.getStatus() == RedeemRequest.Status.APPROVED) {
			// finalize wallet deduction
			FWallet w = req.getWallet();
//			w.setCoins(w.getCoins() - req.getCoins());
//			walletRepo.save(w);

		} else if (req.getStatus() == RedeemRequest.Status.REJECTED || req.getStatus() == RedeemRequest.Status.FAILED) {
			// release reserved coins
			FWallet w = req.getWallet();
			w.setCoins(w.getCoins() + req.getCoins());
			walletRepo.save(w);
		}

		redeemRepo.save(req);
		return req;
	}

	public List<RedeemRequest> getLastReq(long wid) {
		return redeemRepo.findByWallet_WidOrderByCreatedAtDesc(wid);
	}

}
