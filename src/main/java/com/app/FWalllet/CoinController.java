package com.app.FWalllet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coins")
@CrossOrigin(origins = "http://localhost:8082")
public class CoinController {
	
	@Autowired
	CoinTransRepo ctr;

	@GetMapping("/transactions/{wid}")
	public List<CoinTransaction> getCoinTransactions(@PathVariable long wid){
		return ctr.findByFwallet_WidOrderByCreatedAtDesc(wid);
	}
	
	
}
