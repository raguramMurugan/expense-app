package com.hubino.expensetrackingapp.service;

import org.springframework.stereotype.Service;

import com.hubino.expensetrackingapp.dto.SignIn;
import com.hubino.expensetrackingapp.dto.TrackerDto;
import com.hubino.expensetrackingapp.entity.User;
import com.hubino.expensetrackingapp.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	private UserRepository userRepo;
	
	public UserServiceImpl(UserRepository userRepo) {
		this.userRepo=userRepo;
	}

	@Override
	public Integer checkCustomerinDb(SignIn signin) {
		User userinDb=userRepo.findUserByUsernameAndPassword(signin.getUsername(), signin.getPassword());
		return userinDb!=null? userinDb.getUserId():null;
	}

	@Override
	public User saveUser(SignIn signin) {
		User user=new User();
		user.setUsername(signin.getUsername());
		user.setPassword(signin.getPassword());
		user.setEmail(signin.getEmail());
		user.setFirstName(signin.getFirstName());
		user.setLastName(signin.getLastName());
		return userRepo.save(user);
	}

	@Override
	public Integer checkCustomerinDb(TrackerDto dto) {
		User userInDB=userRepo.findUserByUsernameAndPassword(dto.getUsername(), dto.getPassword());
		return userInDB!=null? userInDB.getUserId():null;
	}

	@Override
	public void saveUser(TrackerDto trackerDto) {
		
		User user=new User();
		user.setUsername(trackerDto.getUsername());
		user.setPassword(trackerDto.getPassword());
		userRepo.save(user);
		
	}

	

	
	

}
