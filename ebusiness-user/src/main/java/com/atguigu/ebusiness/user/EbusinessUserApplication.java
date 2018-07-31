package com.atguigu.ebusiness.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.ebusiness.user.mapper")
public class EbusinessUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbusinessUserApplication.class, args);
	}
}
