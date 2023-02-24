package com.hubino.expensetrackingapp.controller;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hubino.expensetrackingapp.customerrorhandler.CustomErrorHandler;
import com.hubino.expensetrackingapp.dto.ResponseTrackerDto;
import com.hubino.expensetrackingapp.dto.SignIn;
import com.hubino.expensetrackingapp.dto.TrackerDto;
import com.hubino.expensetrackingapp.entity.ExpenseTracker;
import com.hubino.expensetrackingapp.entity.User;
import com.hubino.expensetrackingapp.service.ExpenseService;
import com.hubino.expensetrackingapp.service.UserService;

import jakarta.validation.Valid;

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
		public ResponseEntity<?> signIn(@Valid @RequestBody SignIn signin)
		{
			if(!signin.getUsername().isEmpty() && !signin.getPassword().isEmpty())
			{
			User user=new User(signin.getUsername(), signin.getEmail());
			Integer userInDb=userService.checkCustomerinDb1(signin);
			log.info("checking user present in Database:"+" "+userInDb);
			
			if(userInDb!=null)
			{
				user.setUserId(userInDb);
				log.info("User already present in Db with userID:"+ userInDb);
				return CustomErrorHandler.customErrorResponse(CustomErrorHandler.ALREADY_SIGN_IN, HttpStatus.BAD_REQUEST);
			}
			}
			if(!signin.getUsername().isEmpty() && !signin.getEmail().isEmpty())
			{
				userService.saveUser1(signin);
				log.info("user Signed-in Successfully");
				return CustomErrorHandler.successResponse(CustomErrorHandler.SIGN_IN, HttpStatus.ACCEPTED);
			}
			return CustomErrorHandler.customErrorResponse(CustomErrorHandler.FIELD_NOT_GIVEN,HttpStatus.BAD_REQUEST);
		}
		/**
		 * 
		 * @param trackerDto
		 * @return
		 */
		@PostMapping("/expenseTracker")
		public ResponseEntity<?> expenses(@Valid @RequestBody TrackerDto trackerDto)
		{
			if(trackerDto.getUsername()==null || trackerDto.getEmail()==null 
					&& trackerDto.getUsername().isEmpty() || trackerDto.getEmail().isEmpty())
			{
				return CustomErrorHandler.customErrorResponse(CustomErrorHandler.FIELD_NOT_GIVEN, HttpStatus.NOT_ACCEPTABLE);
			}
			else {
			ResponseTrackerDto responseDto=new ResponseTrackerDto();
			Double totalAmount=expenseService.calculateBillAmount(trackerDto.getActivity());
			if(totalAmount==0)
			{
				return CustomErrorHandler.customErrorResponse(CustomErrorHandler.PROVISION_SOLD_OUT, HttpStatus.REQUESTED_RANGE_NOT_SATISFIABLE);
			}
			
			User user=new User(trackerDto.getUsername(), trackerDto.getEmail());
			Integer userPresentInDb=userService.checkCustomerinDb(trackerDto);
			
			if(userPresentInDb!=null)
			{
				user.setUserId(userPresentInDb);
				log.info("User already present in Db with userID:"+" "+userPresentInDb);
			}
			
			ExpenseTracker tracker=new ExpenseTracker(user,trackerDto.getActivity());
			tracker.setTotalAmount(totalAmount);
			expenseService.save(tracker);
			log.info("Order placed Successfully");
			
			responseDto.setExpenseId(tracker.getExpenseId());
			responseDto.setTotalAmount(totalAmount);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(responseDto);
			}
		}
	}


