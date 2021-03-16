package com.zuora.usagedatamapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.zuora.usagedatamapper.repositories")
@EnableJpaAuditing
public class UsageDataMapperApplication {

	public static void main(String[] args) {
		SpringApplication.run(UsageDataMapperApplication.class, args);
	}

}
