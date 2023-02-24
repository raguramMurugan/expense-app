package com.hubino.expensetrackingapp.customerrorhandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hubino.expensetrackingapp.dto.ResponseErrorHandler;
import com.hubino.expensetrackingapp.dto.ResponseSuccessHandler;

public class CustomErrorHandler {
	
	public static final String SIGN_IN="Sign-In Successfully";
	
	public static final String USER_UPDATE_SUCCESSFULLY="User updated successfully";
	
	public static final String USER_DELETE="Selected Id Deleted Successfully";
	
	public static final String ALREADY_SIGN_IN="Already have Account, Kindly Login with your credentials";
	
	public static final String INCORRECT_CREDENTIALS="Username or Email is Incorrect";
	
	public static final String FIELD_NOT_GIVEN="Required field is Blank, Kindly fill the Field";
	
	public static final String PROVISION_NOT_FOUND="Expected Item not found";
	
	public static final String PROVISION_SOLD_OUT="Oops,Expected item sold out";
	
	public static final String CREDENTIALS_NOT_MET="Credentials Should be minimum of 3 charcters";
	
	
	public static ResponseEntity<?> customErrorResponse(String errorStatus, HttpStatus httpStatus)
	{
		ResponseErrorHandler errorHandler=new ResponseErrorHandler();
		
		if(httpStatus.value()==400)
		{
			errorHandler.setErrorStatus(errorStatus);
			return new ResponseEntity<Object>(errorHandler,httpStatus);
		}
		else if(httpStatus.value()==401)
		{
			errorHandler.setErrorStatus(errorStatus);
			return new ResponseEntity<Object>(errorHandler,httpStatus);
		}
		else if(httpStatus.value()==403)
		{
			errorHandler.setErrorStatus(errorStatus);
			return new ResponseEntity<Object>(errorHandler,httpStatus);
		}
		else if(httpStatus.value()==404)
		{
			errorHandler.setErrorStatus(errorStatus);
			return new ResponseEntity<Object>(errorHandler,httpStatus);
		}
		else if(httpStatus.value()==406)
		{
			errorHandler.setErrorStatus(errorStatus);
			return new ResponseEntity<Object>(errorHandler,httpStatus);
		}
		else if(httpStatus.value()==416)
		{
			errorHandler.setErrorStatus(errorStatus);
			return new ResponseEntity<Object>(errorHandler,httpStatus);
		}
		
		return new ResponseEntity<Object>(errorStatus,httpStatus.INTERNAL_SERVER_ERROR);
	}

	
	public static ResponseEntity<?> successResponse(String successStatus, HttpStatus http)
	{
		ResponseSuccessHandler success=new ResponseSuccessHandler();
		if(http.value()==201)
		{
			success.setSuccessStatus(successStatus);
			return new ResponseEntity<Object>(success,http);	
		}
		else if(http.value()==202)
		{
			success.setSuccessStatus(successStatus);
			return new ResponseEntity<Object>(success,http);
		}
		else if(http.value()==208)
		{
			success.setSuccessStatus(successStatus);
			return new ResponseEntity<Object>(success,http);
		}
		
		return new ResponseEntity<Object>(success,HttpStatus.OK);
	}
	

}
