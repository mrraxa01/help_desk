package com.marciorodrigues.order_server_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderServerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServerApiApplication.class, args);
	}

}
