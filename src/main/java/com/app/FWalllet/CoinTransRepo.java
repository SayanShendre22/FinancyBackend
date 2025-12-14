package com.app.FWalllet;

import java.awt.print.Pageable;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinTransRepo extends JpaRepository<CoinTransaction, Long >{

	 List<CoinTransaction> findTop10ByTypeAndFwalletOrderByCreatedAtDesc(
	            String type, FWallet fwallet);
	 
	 List<CoinTransaction> findByFwallet_WidOrderByCreatedAtDesc(long wid);


}
