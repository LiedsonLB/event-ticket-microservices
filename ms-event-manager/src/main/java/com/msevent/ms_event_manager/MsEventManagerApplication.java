package com.msevent.ms_event_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.msevent.ms_event_manager.repositories")
public class MsEventManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsEventManagerApplication.class, args);
	}

}
