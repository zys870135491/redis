package com.zys;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(value = "com.zys.dao")
public class RedisApplication {

	public static void main(String[] args) {
		System.out.println("RedisApplication start");
		SpringApplication.run(RedisApplication.class, args);
		System.out.println("RedisApplication end");
	}

}
