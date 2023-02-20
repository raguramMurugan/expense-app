package com.hubino.expensetrackingapp.service;

import com.hubino.expensetrackingapp.dto.SignIn;
import com.hubino.expensetrackingapp.dto.TrackerDto;
import com.hubino.expensetrackingapp.entity.User;

public interface UserService {


	public Integer checkCustomerinDb(SignIn signin);
	
	public Integer checkCustomerinDb(TrackerDto dto);
	public User saveUser(SignIn signin);

	public void saveUser(TrackerDto trackerDto);

}
