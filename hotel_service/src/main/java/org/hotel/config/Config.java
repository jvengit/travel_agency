package org.hotel.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

import io.micrometer.observation.ObservationRegistry;

@Configuration
public class Config {

	 @Bean
	 public ObservationRegistry observationRegistry() {    
		 return ObservationRegistry.create(); 
	 }
	
////		@LoadBalanced
//		@Bean
//		RestClient.Builder restClientBuilder() {
//			return RestClient.builder();
//		}
	 
}
