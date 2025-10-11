package com.app.budget;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.security.JwtService;
import com.app.user.UserData;
import com.app.user.UserRepo;

@RequestMapping("/budget")
@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class BudgetService {
	
	@Autowired
	BudgetRepository budgetRepo;
	
	@Autowired
	UserRepo ur;
	
	@Autowired
	JwtService jwt;
	
	@PostMapping("//budget")
	public BudgetModel createBudget(@RequestBody BudgetModel budgetModel,@RequestHeader("Authorization") String authHeader) {
		String token = authHeader.replace("Bearer ", "");
		UserData userDetails = ur.getById(jwt.extractUserId(token));
		budgetModel.setUser(userDetails);
		return budgetRepo.save(budgetModel);
	}
	
	
	@PostMapping("/updateBudget")
	public BudgetModel updateBudget(@RequestBody BudgetModel budgetModel) {
		
		BudgetModel b = budgetRepo.getById(budgetModel.getBid());
		
		if(b!=null) {
			b.setCategoryBudgets(budgetModel.getCategoryBudgets());
			b.setMonth(budgetModel.getMonth());
			b.setYear(budgetModel.getYear());
			b.setTotalLimit(budgetModel.getTotalLimit());
			b.setBudgetSetOn(budgetModel.getBudgetSetOn());
			return budgetRepo.save(b);
		}
		
		return null;
	}
	
	@RequestMapping("/deleteBudget/{id}")
	public boolean deleteBudget(@PathVariable long id) {
		budgetRepo.deleteById(id);
		if(budgetRepo.getById(id)!=null) {
			return false;
		}
		return true;
	}
	
	@GetMapping("/getBudget/month/{date}")    // '2015'--year '2025-05'--month
	public List<BudgetModel> getBudget(@PathVariable String date ){
		return budgetRepo.findByYearOrMonth(date);
	}
	
	
}
