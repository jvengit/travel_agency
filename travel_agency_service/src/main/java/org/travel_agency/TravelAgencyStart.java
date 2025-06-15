package org.travel_agency;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import jakarta.annotation.PostConstruct;
import reactor.core.publisher.Hooks;

@SpringBootApplication
@EnableDiscoveryClient
public class TravelAgencyStart {

	public static void main(String[] args) {
		SpringApplication.run(TravelAgencyStart.class, args);
	}

}
