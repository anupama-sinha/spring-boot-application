package com.anupama.sinha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Application {

	@MyContext(name="Anupama",age=20)
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
