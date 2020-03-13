package com.humanresources.assistant;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class AssistantApplication {

//	public static void main(String[] args) {
//		SpringApplication.run(AssistantApplication.class, args);
//	}

	public static void main(String[] args) {
		SpringApplicationBuilder app = new SpringApplicationBuilder(AssistantApplication.class);
		app.build().addListeners(new ApplicationPidFileWriter("./bin/shutdown.pid"));
		app.run();
	}
}
