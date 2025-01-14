package com.ateciot.checkstorelist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(exclude= {SecurityAutoConfiguration.class})
public class CheckStoreListApplication extends SpringBootServletInitializer{
	
	/*for exclude tomcat deploy*/
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(CheckStoreListApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(CheckStoreListApplication.class, args);
	}

}
