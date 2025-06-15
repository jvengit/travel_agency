package org.airport.config;

import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.web.reactive.config.EnableWebFlux;
//import org.springframework.web.reactive.config.WebFluxConfigurer;
//
//import io.micrometer.core.instrument.observation.DefaultMeterObservationHandler;
//import io.micrometer.observation.ObservationHandler;
//import io.micrometer.observation.ObservationRegistry;
//import static reactor.netty.Metrics.OBSERVATION_REGISTRY;
//import io.micrometer.tracing.Tracer;
//import io.micrometer.tracing.handler.DefaultTracingObservationHandler;
//import io.micrometer.tracing.propagation.Propagator;
//import reactor.netty.http.observability.ReactorNettyPropagatingSenderTracingObservationHandler;
//import reactor.netty.observability.ReactorNettyTimerObservationHandler;
//import reactor.netty.observability.ReactorNettyTracingObservationHandler;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.Observation.Context;


@Configuration
//@EnableWebFlux
public class Config/* implements WebFluxConfigurer*/{
	
//	@Bean
//	ObservationRegistry observationRegistry(/*Tracer tracer, Propagator propagator*/) {
////		OBSERVATION_REGISTRY.observationConfig()
////				.observationHandler(
////						new ObservationHandler.FirstMatchingCompositeObservationHandler(
////								new ReactorNettyPropagatingSenderTracingObservationHandler(tracer, propagator),
////								new ReactorNettyTracingObservationHandler(tracer),
////								new DefaultTracingObservationHandler(tracer)));
////		return OBSERVATION_REGISTRY;
//		OBSERVATION_REGISTRY.observationConfig().observationHandler(
//				new ObservationHandler.FirstMatchingCompositeObservationHandler(
//						new ReactorNettyTimerObservationHandler(io.micrometer.core.instrument.Metrics.globalRegistry),
//						new DefaultMeterObservationHandler(io.micrometer.core.instrument.Metrics.globalRegistry)));
//		
//		return OBSERVATION_REGISTRY;
//		
//	}
	
//    @Bean
//    public MetricsWebFilter metricsWebFilter() {
//        return new MetricsWebFilter(Metrics.globalRegistry);
//    }
	
//    @Bean
//    public ObservationRegistry observationRegistry() {
//        return ObservationRegistry.create();
//    }
	
	 @Bean
	 public ObservationHandler<Context> observationHandler(MeterRegistry meterRegistry) {    
		 return new CustomObservationHandler(meterRegistry); 
	 }
}
