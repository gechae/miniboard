package com.ckgexample.miniboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MiniBoardApplication {

	@GetMapping(value = "/hi")
	public String sayHello() {
		return "hello world";
	}
	
	public static void main(String[] args) {
		SpringApplication.run(MiniBoardApplication.class, args);
	}

}
