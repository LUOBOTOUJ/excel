package com.acong.ssoserver.dao;

import org.apache.ibatis.annotations.Mapper;

import com.acong.ssoserver.entity.User;

@Mapper
public interface UserDao {

	void save(User user);

	User findByUsername(String name);
	
}
