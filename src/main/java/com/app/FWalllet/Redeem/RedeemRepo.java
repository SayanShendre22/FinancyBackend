package com.app.FWalllet.Redeem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.FWalllet.Redeem.RedeemRequest.Status;

public interface RedeemRepo extends JpaRepository<RedeemRequest, Long> {
	List<RedeemRequest> findByStatus(RedeemRequest.Status status);
	List<RedeemRequest> findByWallet_WidOrderByCreatedAtDesc(Long wid);
}
