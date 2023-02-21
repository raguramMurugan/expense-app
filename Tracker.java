package com.hubino.expensetrackingapp.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
public class ExpenseTrackerController {
	
	private static final Logger log=LoggerFactory.getLogger(ExpenseTrackerController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ExpenseService expenseService;
		
		/**
		 * 
		 * @param signin
		 * @return
		 */
		
		@PostMapping("/signIn")
		public String signIn(@RequestBody SignIn signin)
		{
			
			User user=new User(signin.getUsername(), signin.getPassword());
			Integer userInDb=userService.checkCustomerinDb(signin);
			log.info("checking user present in Database"+ userInDb);
			
			if(userInDb!=null)
			{
				
				user.setUserId(userInDb);
				log.info("User already present in Db with userID:"+ userInDb);
				return "Account already exists, Kindly Login with your Username and Password";
			}
			else
			{
				
				userService.saveUser(signin);
				log.info("user Signed-in Successfully");
			}
			return "Account Successfully Signed-In";
		}
		
		/**
		 * 
		 * @param trackerDto
		 * @return
		 */
		@PostMapping("/expenseTracker")
		public ResponseTrackerDto expenses(@RequestBody TrackerDto trackerDto)
		{
			ResponseTrackerDto responseDto=new ResponseTrackerDto();
			Double totalAmount=expenseService.calculateBillAmount(trackerDto.getActivity());
			
			User user=new User(trackerDto.getUsername(), trackerDto.getEmail());
			Integer userPresentInDb=userService.checkCustomerinDb(trackerDto);
			
			if(userPresentInDb!=null)
			{
				user.setUserId(userPresentInDb);
				log.info("User already present in Db with userID:"+ userPresentInDb);
			}
			else
			{
				userService.saveUser(trackerDto);
				log.info("user saved to Database Successfully");
			}
			ExpenseTracker tracker=new ExpenseTracker(user,trackerDto.getActivity());
			tracker.setTotalAmount(totalAmount);
			expenseService.save(tracker);
			log.info("Order placed Successfully");
			
			responseDto.setExpenseId(tracker.getExpenseId());
			responseDto.setTotalAmount(totalAmount);
			return responseDto;
		}
	}


