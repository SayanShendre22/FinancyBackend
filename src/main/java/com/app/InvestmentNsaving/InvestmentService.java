package com.app.InvestmentNsaving;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;



@RestController
public class InvestmentService {
	
	@Autowired
	private InvestMentRepo investRepo;
	
	@PostMapping("/invest")
	public InvestmentModel investMoney(@RequestBody InvestmentModel model) {
		return investRepo.save(model);
	}
	
//	@GetMapping("/investment")
//	public List<InvestmentModel> getInvestment( UserData user ){
//		return investRepo.findByUser(user);
//	}

}
