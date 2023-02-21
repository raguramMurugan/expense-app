package com.hubino.expensetrackingapp.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "expense_tracker")
@Data
public class ExpenseTracker {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer expenseId;
	
	@OneToOne
	@JoinColumn(name = "userId")
	private User user;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "expenseId", referencedColumnName = "expenseId")
	private List<Activity> activity;
	
	private Double totalAmount;
	
	public ExpenseTracker(User user,List<Activity>activity)
	{
	this.activity=activity;
	this.user=user;
	}
	

}
