package com.vitality.material.service;

import com.vitality.material.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.vitality.material.mapper.UserDao;
import com.vitality.material.entity.JwtUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao dao;
	
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
    	
        User user = dao.findByUsername(s);
        
        return new JwtUser(user);
    }

}
