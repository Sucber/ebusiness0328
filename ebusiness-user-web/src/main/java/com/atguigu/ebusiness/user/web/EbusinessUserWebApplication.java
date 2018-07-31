package com.atguigu.ebusiness.user.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
public class EbusinessUserWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbusinessUserWebApplication.class, args);
	}
}
