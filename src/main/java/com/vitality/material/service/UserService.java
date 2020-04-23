package com.vitality.material.service;

import com.vitality.material.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vitality.material.mapper.UserDao;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public void save(User user) {
		userDao.save(user);
	}
	
}
