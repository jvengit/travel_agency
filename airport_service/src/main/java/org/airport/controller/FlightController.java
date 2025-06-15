package org.airport.controller;


import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.airport.config.CustomObservationHandler;
import org.airport.dto.FlightDTO;
import org.airport.dto.IFlightDTO;
import org.airport.dto.MessageDTO;
import org.airport.entity.Flight;
import org.airport.exceptions.EntityExistsException;
import org.airport.exceptions.EntityNotFoundException;
import org.airport.exceptions.InvalidEntityException;
import org.airport.service.FlightService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationHandler;
import io.micrometer.observation.ObservationRegistry;
import reactor.core.observability.micrometer.Micrometer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.util.concurrent.Queues;
import io.micrometer.observation.Observation.Context;

@RestController
@RequestMapping("flight")
public class FlightController {

	private final FlightService flightService;
	private ObservationRegistry observationRegistry;
	
	Sinks.Many<Integer> multicastSink = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
	
	public FlightController(FlightService flightService, ObservationRegistry observationRegistry
							//, MeterRegistry meterRegistry, CustomObservationHandler observationHandler 
							) {
		this.flightService = flightService;
//		observationRegistry.observationConfig().observationHandler(observationHandler);
		this.observationRegistry = observationRegistry;
	}
	
	@GetMapping("/all")
	@RateLimiter(name = "limit", fallbackMethod = "onError")
	public ResponseEntity<List<FlightDTO>> finAllFlights(){
		return new ResponseEntity<List<FlightDTO>>(
				flightService.findAllFlights()
				, HttpStatus.OK
		);
	}
	
	@GetMapping("/allni")
	@RateLimiter(name = "limit", fallbackMethod = "onError")
	public ResponseEntity<List<IFlightDTO>> finAllFlightsni(){
		return new ResponseEntity<List<IFlightDTO>>(
				flightService.findAllFlightsni()
				, HttpStatus.OK
		);
	}
	
	@GetMapping("/allne")
	@RateLimiter(name = "limit", fallbackMethod = "onError")
	public ResponseEntity<List<Flight>> finAllFlightsne(){
		return new ResponseEntity<List<Flight>>(
				flightService.findAllFlightsne()
				, HttpStatus.OK
		);
	}
	
	@GetMapping("{id}")
	@CircuitBreaker(name = "serv1", fallbackMethod = "onError")
	public ResponseEntity<MessageDTO<FlightDTO>> findFlightById(@PathVariable(name = "id") Integer id){
		return flightService.findFlightById(id)
							.map((f) -> ResponseEntity.ok(new MessageDTO<FlightDTO>(f)))
							.orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO<FlightDTO>(null)) );
	}
	
	@PostMapping
	public ResponseEntity<MessageDTO<Object>> save(@Validated @RequestBody FlightDTO flightDto){
		try {
			return ResponseEntity.ok(new MessageDTO<Object>(flightService.save(flightDto)));
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO<Object>(ex.getMessage()) );
		}
	}
	
	@PutMapping
	public ResponseEntity<MessageDTO<Object>> update(@Validated @RequestBody FlightDTO flightDto){
		try {
			return ResponseEntity.ok(new MessageDTO<Object>(flightService.update(flightDto)));
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO<Object>(ex.getMessage()) );
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<MessageDTO<String>> delete(@PathVariable("id") Integer id){
		try {
			flightService.delete(id);
			return ResponseEntity.ok(new MessageDTO<String>("flight deleted"));
		} catch (InvalidEntityException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO<String>(ex.getMessage()) );
		}
	}
	
	@GetMapping("rmTest")
	public Flux<Integer> testRm() throws InterruptedException{

//		Observation observation = Observation./*createNotStarted*/start("Myflights", this.observationRegistry);
		
		Flux<Integer> publish = this.multicastSink.asFlux()
									.name("test1")
									.tag("foo", "bar")
									.tap(Micrometer.observation(this.observationRegistry
																, registry -> observationRegistry.getCurrentObservation()
//																, registry -> observation
//																, registry -> Observation.start("Myflights", this.observationRegistry)
										))
									 .doOnEach(signal -> {
//									        // Ensure the observation is active before triggering an event
//									        if (signal.hasValue()) {
										 
//									            if (observation != null) {
												 this.observationRegistry.getCurrentObservation()
												 	.event(Observation.Event.of("flightsEvent", "List of all flightsffff"));
//									            }
//									        }
									    })
									
//									.doOnEach(signal -> 
//									        	observation.event(Observation.Event.of("flightsEvent", "List of all flights")) )
//									.doOnComplete(() -> observation.stop())
//									.doOnError((e) -> {
//													   observation.error(e);
//													   observation.stop();
//													   })
							 		.delayElements(Duration.ofSeconds(2) );
        return publish;
	}
	
	@GetMapping("startmcemit")
	public void testmc(){
		
		Stream.iterate(0, o -> o+1)
			  .limit(20)
			  .forEach(e -> this.multicastSink.tryEmitNext(e).isSuccess() );

	}
	
	@GetMapping("stopmcemit")
	public void stopmc() {
		System.out.println("Last - "+this.multicastSink.tryEmitComplete().isSuccess());
		this.multicastSink = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
	}
	
	@GetMapping("restartmcemit")
	public void restartmc() {
		this.multicastSink = Sinks.many().multicast().onBackpressureBuffer(Queues.SMALL_BUFFER_SIZE, false);
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
			   ex.getAllErrors()
				 	.stream()
				 	.collect(Collectors.toMap(
						 (k) -> ((FieldError)k).getField()
						 , (v) -> v.getDefaultMessage()
					))
		);
	}
	
    private ResponseEntity<String> onError(HttpServerErrorException thr){
        return ResponseEntity.status(HttpStatus.OK).body("limit reached");
    }
//	    @Retry(name = "rtr", fallbackMethod = "onError")
//    	@RateLimiter(name = "limit", fallbackMethod = "onError")
//      @CircuitBreaker(name = "serv1", fallbackMethod = "onError")
	
}
