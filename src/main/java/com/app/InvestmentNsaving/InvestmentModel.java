package com.app.InvestmentNsaving;

import java.time.LocalDateTime;
import java.util.List;

import com.app.user.UserData;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;

@Entity
public class InvestmentModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	long id;
	double amount;
	String type;   // FD MF STOCKS
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "user_id") // foreign key column
//	private UserData user;


//	public InvestmentModel(long id, double amount, String type, UserData user, LocalDateTime investedDate) {
//		this.id = id;
//		this.amount = amount;
//		this.type = type;
//		this.user = user;
//		this.investedDate = investedDate;
//	}

//	public UserData getUser() {
//		return user;
//	}

//	public void setUser(UserData user) {
//		this.user = user;
//	}

	private LocalDateTime investedDate;
    
    @PrePersist
    public void prePersist() {
        this.investedDate = LocalDateTime.now();
    }

	public InvestmentModel(long id, double amount, String type,
			LocalDateTime investedDate) {
		this.id = id;
		this.amount = amount;
		this.type = type;
		this.investedDate = investedDate;
	}

	public InvestmentModel() {
		super();
		// TODO Auto-generated constructor stub
	}

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


	public LocalDateTime getInvestedDate() {
		return investedDate;
	}

	public void setInvestedDate(LocalDateTime investedDate) {
		this.investedDate = investedDate;
	}

	
	
  

}
