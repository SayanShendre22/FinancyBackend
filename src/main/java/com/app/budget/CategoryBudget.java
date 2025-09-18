package com.app.budget;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CategoryBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cid;

    private String category;        // Food, Rent, etc.
    private double limitAmount;       // Budget limit for this category

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private BudgetModel budget;
    

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}


	public double getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(double limitAmount) {
		this.limitAmount = limitAmount;
	}

	public BudgetModel getBudget() {
		return budget;
	}

	public void setBudget(BudgetModel budget) {
		this.budget = budget;
	}

	public CategoryBudget(Long cid, String category, double limit, BudgetModel budget) {
		this.cid = cid;
		this.category = category;
		this.limitAmount = limit;
		this.budget = budget;
	}

	public CategoryBudget() {
		super();
		// TODO Auto-generated constructor stub
	}
    
    
	
}
