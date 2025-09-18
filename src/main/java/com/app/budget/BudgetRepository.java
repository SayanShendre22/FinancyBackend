package com.app.budget;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository  extends JpaRepository<BudgetModel,Long > {
	
	@Query(value = "SELECT * FROM budget_repository WHERE DATE_FORMAT(budget_set_on, '%Y-%m') LIKE CONCAT(:date, '%')", nativeQuery = true)
	List<BudgetModel> findByYearOrMonth(@Param("date") String date);


}
