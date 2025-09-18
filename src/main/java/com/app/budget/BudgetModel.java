package com.app.budget;

import java.time.LocalDateTime;
import java.util.List;

import com.app.user.UserData;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class BudgetModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bid;

    private double totalLimit;   // Total monthly budget

    private Integer month;
    private Integer year;
    
    private LocalDateTime budgetSetOn;
	
    @PrePersist
    public void prePersist() {
        this.budgetSetOn = LocalDateTime.now();
    }

    @ManyToOne
    @JoinColumn(name = "user_id")
//    @JsonBackReference   // ✅ breaks recursion
    private UserData user;

    public LocalDateTime getBudgetSetOn() {
		return budgetSetOn;
	}

	public void setBudgetSetOn(LocalDateTime budgetSetOn) {
		this.budgetSetOn = budgetSetOn;
	}

	@OneToMany(mappedBy = "budget", cascade = CascadeType.ALL)
    private List<CategoryBudget> categoryBudgets;

	public Long getBid() {
		return bid;
	}

	public void setBid(Long bid) {
		this.bid = bid;
	}

	public double getTotalLimit() {
		return totalLimit;
	}

	public void setTotalLimit(double totalLimit) {
		this.totalLimit = totalLimit;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public UserData getUser() {
		return user;
	}

	public void setUser(UserData user) {
		this.user = user;
	}

	public List<CategoryBudget> getCategoryBudgets() {
		return categoryBudgets;
	}

	public void setCategoryBudgets(List<CategoryBudget> categoryBudgets) {
		this.categoryBudgets = categoryBudgets;
	}

	public BudgetModel(Long bid, double totalLimit, Integer month, Integer year, UserData user,
			List<CategoryBudget> categoryBudgets) {
		this.bid = bid;
		this.totalLimit = totalLimit;
		this.month = month;
		this.year = year;
		this.user = user;
		this.categoryBudgets = categoryBudgets;
	}

	public BudgetModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	
}



