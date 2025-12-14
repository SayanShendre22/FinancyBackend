package com.app.FWalllet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.app.user.UserData;

@Repository
public interface FWalletRepo extends JpaRepository<FWallet, Long> {
	 FWallet findByUser(UserData user);
}
