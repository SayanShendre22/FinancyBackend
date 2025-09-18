package com.app.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionModel, Long>{
	List<TransactionModel> findByBankAccountId(Long accountId);
	
	List<TransactionModel> findByCategory(String category);
}
