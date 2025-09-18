package com.app.user;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.app.balanceAcc.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.jsonwebtoken.lang.Collections;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserData implements UserDetails{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String username;
	private String email;
	private String password;
	private LocalDateTime registeredAt; // 👈 Date-Time field

	public LocalDateTime getRegisteredAt() {
		return registeredAt;
	}

	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}
	
//	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//	@JsonManagedReference   // ✅ keeps balances in user
	private List<BalanceModel> balances;

	public UserData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<BalanceModel> getBalances() {
		return balances;
	}

	public void setBalances(List<BalanceModel> balances) {
		this.balances = balances;
	}

	public UserData(long id, String username, String email, String password, List<BalanceModel> balances) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.balances = balances;
	}
	
	

	@Override
	public String toString() {
		return "UserData [id=" + id + ", username=" + username + ", email=" + email + ", password=" + password
				+ ", registeredAt=" + registeredAt + ", balances=" + balances + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		  return new ArrayList<>(); // You can add roles later
	}
	
	  @Override
	    public boolean isAccountNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isAccountNonLocked() {
	        return true;
	    }

	    @Override
	    public boolean isCredentialsNonExpired() {
	        return true;
	    }

	    @Override
	    public boolean isEnabled() {
	        return true;
	    }

}
