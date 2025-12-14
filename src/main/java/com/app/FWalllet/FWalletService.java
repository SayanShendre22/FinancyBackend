package com.app.FWalllet;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.user.UserData;

@Service
public class FWalletService {

	@Autowired
	private FWalletRepo fwr;

	@Autowired
	private CoinTransRepo ctr;

	// create wallet
	public FWallet createWallet(UserData user) {
		FWallet wallet = new FWallet();
		wallet.setUser(user);
		wallet.setActiveStatus(1);
		return fwr.save(wallet);
	}

	// update balance
	@Transactional
	public ResponseEntity<FWallet> updateBalance(long amount, FWallet wallet, String type) {
		// today
		ZoneId zone = ZoneId.systemDefault();
		LocalDate targetDate = LocalDate.now(zone);	
		List<CoinTransaction> list =
			    ctr.findTop10ByTypeAndFwalletOrderByCreatedAtDesc(type, wallet);
		
		if(list!=null || list.size()>0) {
			
			if (type.equalsIgnoreCase("daily")) {
				if(list.size()>0 && list.get(0).getCreatedAt().toLocalDate().equals(targetDate)) {
					return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
				}
				
			} else if (type.toLowerCase() == "image") {
				
				int count =0;
				for(CoinTransaction x:list) {
					if(x.getCreatedAt().toLocalDate().equals(targetDate)) {
						count++;
						if(count == 10) {
							return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
						}
					}
					if(count>=10) {
						return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
					}
				}

			} else if (type.toLowerCase() == "video") {
				int count =0;
				for(CoinTransaction x:list) {
					if(x.getCreatedAt().toLocalDate().equals(targetDate)) {
						count++;
						if(count == 5) {
							return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
						}
					}
					if(count>=5) {
						return new ResponseEntity<>(null, HttpStatus.ALREADY_REPORTED);
					}
					
				}

			}
		}

		wallet.setCoins(wallet.getCoins() + amount);
		CoinTransaction ct = new CoinTransaction(amount, type, wallet);
		ctr.save(ct);
		FWallet fw = fwr.save(wallet);
		return new ResponseEntity<FWallet>(fw, HttpStatus.CREATED);
	}

	// get wallet
	public FWallet getWallet(UserData user) {
		FWallet wallet = fwr.findByUser(user);
		return wallet;
	}

	// enable/disbale wallet
	public FWallet updateStatus(int status, long wid) {
		FWallet wallet = fwr.getById(wid);
		wallet.setActiveStatus(status);
		return wallet;
	}
}
