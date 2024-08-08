package com.example.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LibraryMangementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryMangementSystemApplication.class, args);
	}

}
