package com.skyhook.testapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(exclude = {HibernateJpaAutoConfiguration.class })
@EnableScheduling
public class TestappApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestappApplication.class, args);
	}
}