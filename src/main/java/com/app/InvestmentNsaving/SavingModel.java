package com.app.InvestmentNsaving;

import com.app.user.UserData;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class SavingModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	double amount;
	
//	@OneToMany
//	@JoinColumn(name = "user_id")
//    private UserData user;
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

//	public UserData getUser() {
//		return user;
//	}

//	public void setUser(UserData user) {
//		this.user = user;
//	}

//	public SavingModel(long id, double amount, UserData user) {
//		this.id = id;
//		this.amount = amount;
//		this.user = user;
//	}

	public SavingModel() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
}
