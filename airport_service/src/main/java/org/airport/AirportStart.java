package org.airport;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableDiscoveryClient
public class AirportStart {

	public static void main(String[] args) {
		SpringApplication.run(AirportStart.class, args);
	}

//Observation - trace propagation - include observation span in correct trace - no need for observationRegistry in config
	@PostConstruct
	public void init() {
		Hooks.enableAutomaticContextPropagation();
	}
}
