package com.hubino.expensetrackingapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hubino.expensetrackingapp.dto.ResponseTrackerDto;
import com.hubino.expensetrackingapp.dto.SignIn;
import com.hubino.expensetrackingapp.dto.TrackerDto;
import com.hubino.expensetrackingapp.entity.ExpenseTracker;
import com.hubino.expensetrackingapp.entity.User;
import com.hubino.expensetrackingapp.service.ExpenseService;
import com.hubino.expensetrackingapp.service.UserService;
import com.hubino.expensetrackingapp.service.UserServiceImpl;

@RestController
public class Tracker {
	
	private UserService userService;
	
	private ExpenseService expenseService;
	
	public Tracker(ExpenseService expenseService)
	{
		this.expenseService=expenseService;
	}
	
	public Tracker(UserServiceImpl userService)
	{
		this.userService=userService;
	}
	
	@PostMapping("/signIn")
	public String signIn(@RequestBody SignIn signin)
	{
		User user=new User(signin.getUsername(), signin.getPassword());
		Integer userInDb=userService.checkCustomerinDb(signin);
		if(userInDb!=null)
		{
			user.setUserId(userInDb);
			return "Account already exists, Kindly Login with your Username and Password";
		}
		else
		{
			userService.saveUser(signin);
		}
		return "Account Successfully Signed-In";
	}

	@PostMapping("/expenseTracker")
	public ResponseTrackerDto expenses(@RequestBody TrackerDto trackerDto)
	{
		ResponseTrackerDto responseDto=new ResponseTrackerDto();
		
		User user=new User(trackerDto.getUsername(), trackerDto.getPassword());
		Integer userPresentInDb=userService.checkCustomerinDb(trackerDto);
		if(userPresentInDb!=null)
		{
			user.setUserId(userPresentInDb);
		}
		else
		{
			userService.saveUser(trackerDto);
		}
		Double totalAmount=expenseService.calculateBillAmount(trackerDto);
		return null;
	}
}
