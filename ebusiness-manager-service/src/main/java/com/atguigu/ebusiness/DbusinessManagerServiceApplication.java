package com.atguigu.ebusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.atguigu.ebusiness.manager.mapper")
public class DbusinessManagerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DbusinessManagerServiceApplication.class, args);
	}
}
