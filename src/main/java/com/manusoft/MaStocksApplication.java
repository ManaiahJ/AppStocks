package com.manusoft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class })
public class MaStocksApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaStocksApplication.class, args);
	}

}
