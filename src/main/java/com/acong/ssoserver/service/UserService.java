package com.acong.ssoserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.acong.ssoserver.dao.UserDao;
import com.acong.ssoserver.entity.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao userDao;

	public void save(User user) {
		userDao.save(user);
	}
	
}
