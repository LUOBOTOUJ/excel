package com.vitality;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApp {

	public static void main(String[] args) {
		SpringApplication.run(ServerApp.class, args);

		ExcelService excelService = new ExcelService();
		try {
			excelService.insertByExcel("/Users/egoshiten/Desktop/aaa");
		}catch (Exception e){

		}
	}
}
