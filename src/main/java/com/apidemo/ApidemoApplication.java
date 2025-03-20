package com.apidemo;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApidemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApidemoApplication.class, args);
	}

	// we can use user define config package also to add external java dependency and also can use main method classs for configuration
	@Bean
	public ModelMapper getModelMapper   () {
		return  new ModelMapper();
	}
}
