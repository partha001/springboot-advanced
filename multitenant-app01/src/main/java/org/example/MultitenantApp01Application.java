package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication
public class MultitenantApp01Application {

	public static void main(String[] args) {
		SpringApplication.run(MultitenantApp01Application.class, args);
	}

}
