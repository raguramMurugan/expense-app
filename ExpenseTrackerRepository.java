package com.hubino.expensetrackingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hubino.expensetrackingapp.entity.ExpenseTracker;

@Repository
public interface ExpenseTrackerRepository extends JpaRepository<ExpenseTracker, Integer>{

}
