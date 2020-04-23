package com.vitality.material.mapper;

import com.vitality.material.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserDao {

	void save(User user);

	User findByUsername(String name);
	
}
