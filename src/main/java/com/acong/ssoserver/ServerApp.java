package com.acong.ssoserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan({"com.acong.ssoserver.material.mapper","com.acong.ssoserver.dao"})
public class ServerApp {

	public static void main(String[] args) {
		SpringApplication.run(ServerApp.class, args);
	}


}
