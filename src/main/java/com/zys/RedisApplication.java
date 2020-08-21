package com.zys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RedisApplication {

	public static void main(String[] args) {
		System.out.println("RedisApplication start");
		SpringApplication.run(RedisApplication.class, args);
		System.out.println("RedisApplication end");
	}

}
