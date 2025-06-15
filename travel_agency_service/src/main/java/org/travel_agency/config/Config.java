package org.travel_agency.config;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;

import io.micrometer.tracing.Tracer;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation.Context;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationRegistry;


@Configuration
public class Config {
	
	@Autowired
	private MeterRegistry meterRegistry;
	
	@Autowired
    private ReactorLoadBalancerExchangeFilterFunction lbFunction;
	
	@LoadBalanced
	@Bean
	RestClient.Builder restClientBuilder(Tracer tracer) {
		
		return RestClient.builder()
						 .defaultRequest(new CustomRCRequestHeaders(tracer));
	}
	
	

	
//	@Bean
//    public Propagator b3MultiPropagator() {
//        Propagation.Factory factory = B3Propagation.FACTORY;
//        return BravePropagator.create(factory);
//    }
	
	 @Bean
	 @LoadBalanced
	 WebClient webClient(WebClient.Builder builder,@Value("${url}") String url, Tracer tracer) {
	     return builder.baseUrl("http://GATEWAY/")
	        		.filter(lbFunction)
	        		.filter((ClientRequest request, ExchangeFunction next) ->{
	        			System.out.println("///////////////////////////////////////////////////"+request.attributes());
	        			for (Map.Entry<String, Object> entry : request.attributes().entrySet()) {
							System.out.println("******KEY***********"+entry.getKey()+"****VAL*****"+entry.getValue());
						}
	        			return next.exchange(request);
	        		})
//	        		.filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
//	        			System.out.println(clientResponse
//	        								.bodyToMono(ClientResponse.class)
////	        								.map(t -> )
//	        								);
//	        			return clientResponse.bodyToMono(ClientResponse.class);
//	        		}))
	        		.defaultRequest(new CustomWCRequestHeaders(tracer))
	        		.build();
	 }
	    
	 @Bean
	 public ObservationRegistry observationRegistry() {
		 ObservationRegistry registry = ObservationRegistry.create();
		 registry.observationConfig().observationHandler(observationHandler());
		 return registry;
	 }
	 
	 @Bean
	 public ObservationHandler<Context> observationHandler() {    
		 return new CustomObserverHandler(this.meterRegistry); 
	 }
	 
	 
//	 @Bean
//	   public CountedAspect countedAspect(MeterRegistry registry) {
//	      return new CountedAspect(registry);
//	   }
}
