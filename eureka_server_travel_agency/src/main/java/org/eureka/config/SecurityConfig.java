package org.eureka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((authz) -> authz
				.anyRequest().authenticated()
	        )
		    .httpBasic(Customizer.withDefaults());
	    http.csrf(
//	    		Customizer.withDefaults()
	    		(csrf) -> csrf.disable()//ignoringRequestMatchers("/eureka/**")
	    		);
	    return http.build();
	}
	
}
