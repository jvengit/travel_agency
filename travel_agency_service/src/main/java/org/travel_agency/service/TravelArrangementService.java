package org.travel_agency.service;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;
import org.travel_agency.dao.TravelArrangementDAO;
import org.travel_agency.dto.BookingDTO;
import org.travel_agency.dto.FlightDTO;
import org.travel_agency.dto.TravelArrangementDTO;
import org.travel_agency.entity.TravelArrangement;
import org.travel_agency.exceptions.EntityExistsException;
import org.travel_agency.exceptions.EntityNotFoundException;
import org.travel_agency.exceptions.InvalidEntityException;
import org.travel_agency.mappers.TravelArrangementMapper;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;
import io.micrometer.tracing.ScopedSpan;
import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;

@Service
public class TravelArrangementService {
	final static Logger logger = LoggerFactory.getLogger(TravelArrangementService.class);
	private final TravelArrangementDAO travelArrangementDao;
	private final TravelArrangementMapper travelArrangementMapper = Mappers.getMapper(TravelArrangementMapper.class);
	private RestClient.Builder restClientBuilder;
	private Tracer tracer;
    private WebClient webClient;
    private MeterRegistry meterRegistry;
    private Timer timer;
    private Counter counter;
    private Gauge gauge;
    private ObservationRegistry observationRegistry;
    
	public TravelArrangementService(TravelArrangementDAO travelArrangementDao, RestClient.Builder restClientBuilder, Tracer tracer, WebClient webClient, MeterRegistry meterRegistry, ObservationRegistry observationRegistry) {
		super();
		this.travelArrangementDao = travelArrangementDao;
		this.restClientBuilder = restClientBuilder;
		this.tracer = tracer;
		this.webClient = webClient;
		this.meterRegistry = meterRegistry;
		this.observationRegistry = observationRegistry;
		
		this.timer = Timer
    		    .builder("my.timer")
    		    .description("a description of what this timer does") // optional
    		    .tags("region", "test") // optional
    		    .register(meterRegistry);
		
		this.counter = Counter
			    .builder("counter")
			    .description("a description of what this counter does") // optional
			    .tags("region", "test") // optional
			    .register(meterRegistry);
		
	}

	public List<TravelArrangementDTO> findAllTravelArrangements() {
		List<TravelArrangementDTO> travelArrangements = travelArrangementDao.findAllTravelArrangements();
		return travelArrangements;
	}
	
	public Optional<TravelArrangementDTO> findTravelArrangementById(Integer id) {
		Optional<TravelArrangementDTO> travelArrangement = travelArrangementDao.findTravelArrangementById(id);
		if(travelArrangement.isPresent()) {
			return travelArrangement;
		}
		return Optional.empty();
	}
	
	@Transactional
	public TravelArrangementDTO save(TravelArrangementDTO travelArrangementDTO) throws EntityExistsException {
		return Optional.of(travelArrangementDTO)
				.map( (b) -> travelArrangementMapper.toDto(travelArrangementDao.save(travelArrangementMapper.toEntity(b))) )
				.orElseThrow(() -> {return new EntityExistsException(travelArrangementDTO, "Travel arrangement exists");});	
//		if(travelArrangementDao.findTravelArrangementById(travelArrangementDTO.id()).isPresent())
//			throw new EntityExistsException(travelArrangementDTO, "Travel arrangement exists");
//		else {
//			TravelArrangement travelArrangement = travelArrangementDao.save(travelArrangementMapper.toEntity(travelArrangementDTO));
//			return travelArrangementMapper.toDto(travelArrangement);
//		}		
	}
	
	@Transactional
	public TravelArrangementDTO update(TravelArrangementDTO travelArrangementDTO) throws EntityNotFoundException {
		if(travelArrangementDao.findTravelArrangementById(travelArrangementDTO.id()).isEmpty())
			throw new EntityNotFoundException(travelArrangementDTO, "Travel arrangement does not exists");
		else {
			logger.info(travelArrangementMapper.toEntity(travelArrangementDTO).toString());
			TravelArrangement travelArrangement = travelArrangementDao.save(travelArrangementMapper.toEntity(travelArrangementDTO));
			return travelArrangementMapper.toDto(travelArrangement);
		}
	}
	
	@Transactional
	public void delete(Integer id) throws InvalidEntityException {
		Optional<TravelArrangement> travelArrangement = travelArrangementDao.findById(id);
		if(travelArrangement.isEmpty())
			throw new InvalidEntityException(id, "Invalid travel arrangement");
		else {
			travelArrangement.orElseGet(null).setStatus(3);
		}
	}
	
	
	    public void processRequest() {
	    	this.counter.increment();
	        try {
	            Thread.sleep(1000); 
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }
	
	public List<BookingDTO> findAllBookings() {
		
		this.timer.record(() -> processRequest());
		List<BookingDTO> bookings = null;
		this.gauge = Gauge
			    .builder("gauge", bookings, List::size)
			    .description("a description of what this gauge does") // optional
			    .tags("region", "test") // optional
			    .register(meterRegistry);
		
		Span span = tracer.currentSpan();
		processRequest();
//		processRequest();
//		processRequest();
		
		span.tag("timerrrrrrrrrrrrrrrr", this.timer.totalTime(TimeUnit.MILLISECONDS) );
		span.tag("counterrrrrrrrrrrrrrrr", this.counter.count() );
		
		processRequest();
		
		Observation observation = Observation.start("Myflightswwwww", this.observationRegistry);
		observation.lowCardinalityKeyValue("qqqqq", "sssssss");
		
		try (Observation.Scope ignored = observation.openScope()){
		bookings = restClientBuilder
//				.build().get()
				.baseUrl("http://GATEWAY/")
//			.defaultHeaders(
//		            httpHeaders -> {
//		                httpHeaders.set("X-B3-TraceId", span.context().traceId());
//		                httpHeaders.set("X-B3-ParentSpanId", span.context().parentId());
//		                httpHeaders.set("X-B3-SpanId", span.context().spanId());
//		                httpHeaders.set("X-B3-Sampled", span.context().sampled().toString());
//		              })
			.build().get()
			.uri("booking/all")
//			.uri(URI.create("http://HOTEL/booking/all"))
			.accept(MediaType.APPLICATION_JSON)
			.retrieve()
			.onStatus(status -> status.value() == 500, (request, response) -> {
				logger.info("************************error 500 recieved");
			  })
			.body(new ParameterizedTypeReference<>() {});
		observation.event(Observation.Event.of("flightsEvent", "List of all flights"));
		observation.event(Observation.Event.of("flightsEvent2", "List of all flights2"));
		
		span.tag("gauge", this.gauge.value() );
		}catch(Exception ex) {
			observation.error(ex);
		}finally {
			observation.stop();
		}
//		logger.info(bookings.toString());
		return bookings;
	}
	
	public List<FlightDTO> findAllFlights() {
		List<FlightDTO> flights = null;
		Span span = tracer.currentSpan();

		System.out.println("X-B3-TraceId " + span.context().traceId());
		System.out.println("X-B3-ParentSpanId " + span.context().parentId());
		System.out.println("X-B3-SpanId " + span.context().spanId());
		System.out.println("X-B3-Sampled " + span.context().sampled().toString());
		
		System.out.println(this.observationRegistry);
		Observation observation = Observation.start("Myflights", this.observationRegistry);
		
		try (Observation.Scope scope = observation.openScope()){
			Thread.sleep(3000);
			flights = restClientBuilder.baseUrl("http://GATEWAY/")
//					.defaultHeaders(
//				            httpHeaders -> {
//				                httpHeaders.set("X-B3-TraceId", span.context().traceId());
//				                httpHeaders.set("X-B3-ParentSpanId", span.context().parentId());
//				                httpHeaders.set("X-B3-SpanId", span.context().spanId());
//				                httpHeaders.set("X-B3-Sampled", span.context().sampled().toString());
//				              })
					.build().get()
					.uri("flight/all")
					.accept(MediaType.APPLICATION_JSON)
					.retrieve()
					.onStatus(status -> status.value() == 500, (request, response) -> {
						logger.info("************************error 500 recieved");
					  })
					.body(new ParameterizedTypeReference<>() {});
			observation.event(Observation.Event.of("flightsEvent", "List of all flights"));
			observation.event(Observation.Event.of("flightsEvent2", "List of all flights2"));
			
		} catch(Exception ex) {
			observation.error(ex);
		}finally {
			observation.stop();
		}
		observation.event(Observation.Event.of("flightsEvent33", "List of all flights33"));
		
		
		return flights;
	}

	public String findFlightDetails(){
		
//			ScopedSpan span = tracer.startScopedSpan("");
			Span span = tracer.currentSpan();
//				Mono<String> servFlux = 
	////		flux = 
			webClient.get()
				  .uri(("/flight/rmTest"))
////				  .headers(httpHeaders -> {
////		                httpHeaders.set("X-B3-TraceId", span.context().traceId());
////		                httpHeaders.set("X-B3-ParentSpanId", span.context().parentId());
////		                httpHeaders.set("X-B3-SpanId", span.context().spanId());
////		                httpHeaders.set("X-B3-Sampled", span.context().sampled().toString());
////					})
				  .retrieve()
				  .bodyToFlux(Integer.class)//String.class - concatenates - spring docs - use Flux#collectToList() and <List<String>>
	//			  .filter(p->!p.equals('3'))
	//			  .timeout(Duration.ofMillis(2000))
	//			  .onErrorContinue(TimeoutException.class,
	//					  (error, obj) -> logger.info("error:[{}], obj:[{}]", error, obj))
	//			  .doOnError(TimeoutException.class, ex -> logger.info("WriteTimeout"))
	//			  .subscribe(
	//					  data->logger.info("data - "+data)
	//					  ,error->error.getMessage()//error.printStackTrace()
	//					  )
				  .log().subscribe(data->logger.info("data - "+data));
						  ;
	////				dispFlux = flux.log().subscribe(data->logger.info("data - "+data));
				  
				  
				  
	//			  .subscribeWith(new BaseSubscriber<Object>() {
	//				  @Override
	//			        protected void hookOnSubscribe(Subscription subscription) {
	//			            // Request the first 5 elements upon subscription
	//			            request(5);
	//			        }
	//
	//			        // Hook executed when a new element is received
	//			        @Override
	//			        protected void hookOnNext(Object value) {
	//			            // Process the received value
	//			        	logger.info("data - "+value.toString());
	//			            
	//			            // Request the next 10 elements when a multiple of 10 is encountered
	////			            if (value % 10 == 0) {
	////			                request(10);
	////			            }
	//			            
	////			            if (Integer.parseInt(value.toString()) == 5 ) {
	////			            	logger.info("requested 5 more elements");
	////			            	request(5);
	////			            }
	//			        }
	//			  }
	//					  		);
			
						
						
						
						
	//			})
	//			  .subscribe(
	//					  data->logger.info("data - "+data)
	//					  ,error->error.getMessage()//error.printStackTrace()
	//					  );
	//		}
						
						
						
	//					try {
	//						Thread.sleep(10000);
	//					} catch (InterruptedException e) {
	//						// TODO Auto-generated catch block
	//						e.printStackTrace();
	//					}
//			logger.info("*******-------------------------------*****************second call");
			
			
	//		webClient.get()
	//		  .uri(("/serv2/service2rmTest"))
	//		  .retrieve()
	//		  .bodyToFlux(Character.class)//String.class - concatenates - spring docs - use Flux#collectToList() and <List<String>>
	////		  .filter(p->!p.equals('3'))
	////		  .timeout(Duration.ofMillis(2000))
	//		  .onErrorContinue(TimeoutException.class,
	//				  (error, obj) -> logger.info("error:[{}], obj:[{}]", error, obj))
	//		  .doOnError(TimeoutException.class, ex -> logger.info("WriteTimeout"))
	////		  .subscribeWith(new BaseSubscriber<Character>() {
	////			  @Override
	////		        protected void hookOnSubscribe(Subscription subscription) {
	////		            // Request the first 5 elements upon subscription
	////		            request(5);
	////		        }
	////
	////		        // Hook executed when a new element is received
	////		        @Override
	////		        protected void hookOnNext(Character value) {
	////		            // Process the received value
	////		        	logger.info("data from second call - "+value.toString());
	////		            
	////		            // Request the next 10 elements when a multiple of 10 is encountered
	//////		            if (value % 10 == 0) {
	//////		                request(10);
	//////		            }
	////		            
	////		            if (Integer.parseInt(value.toString()) == 5 ) {
	////		            	logger.info("requested 5 more elements");
	////		            	request(5);
	////		            }
	////		        }
	////		  }
	////				  		)
	//		  .subscribe(
	//		  data->logger.info("data from secon call - "+data)
	//		  ,error->error.getMessage()//error.printStackTrace()
	//		  )
	//		  ;
	//		/*return*/ webClient.get()
	//			  .uri(("/serv2/service2Test"))
	//			  .retrieve()
	//			  .bodyToMono(String.class)
	//			  .subscribe(
	//					  data->logger.info("data - "+data)
	//					  );
			return "body from serv1";
		}
	
	
}
