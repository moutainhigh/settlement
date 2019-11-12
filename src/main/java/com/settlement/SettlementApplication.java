package com.settlement;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.settlement.mapper")
public class SettlementApplication {

	public static void main(String[] args) {
		SpringApplication.run(SettlementApplication.class, args);
	}

}
