package com.app.balanceAcc;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.user.UserData;


@Repository
public interface BalanceRepo extends JpaRepository<BalanceModel, Long> {
	List<BalanceModel> findByUser(UserData user);
}
