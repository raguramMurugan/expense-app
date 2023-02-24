package com.hubino.expensetrackingapp.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.hubino.expensetrackingapp.entity.Activity;
import com.hubino.expensetrackingapp.entity.ExpenseTracker;

public interface ExpenseService {

	public Double calculateBillAmount(List<Activity> activity);

	public void save(ExpenseTracker tracker);

}
