package com.hubino.expensetrackingapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hubino.expensetrackingapp.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	User findUserByUsernameAndEmail(String userName, String email);

}
