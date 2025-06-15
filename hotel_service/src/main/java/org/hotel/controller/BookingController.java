package org.hotel.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.hotel.dto.BookingDTO;
import org.hotel.dto.MessageDTO;
import org.hotel.exceptions.EntityExistsException;
import org.hotel.exceptions.EntityNotFoundException;
import org.hotel.exceptions.InvalidEntityException;
import org.hotel.service.BookingService;
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

@RestController
@RequestMapping("booking")
public class BookingController {
	
	private final BookingService bookingService;

	public BookingController(BookingService bookingService) {
		super();
		this.bookingService = bookingService;
	}
	
	@GetMapping(value = "/all")
	@RateLimiter(name = "limit", fallbackMethod = "onError")
	public ResponseEntity<List<BookingDTO>> findAllBookings(){
		return new ResponseEntity<List<BookingDTO>>(
				bookingService.findAllBookings()
				, HttpStatus.OK
		);
	}

	@GetMapping(value = "{id}")
	@CircuitBreaker(name = "serv1", fallbackMethod = "onError")
	public ResponseEntity<MessageDTO<BookingDTO>> findBookingById(@PathVariable(name = "id") Integer id){
		return bookingService.findBookingById(id)
                .map(h -> ResponseEntity.ok( new MessageDTO<BookingDTO>(h) ))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO<BookingDTO>(null))
        );
	}
	
	@PostMapping
	public ResponseEntity<MessageDTO<Object>> save(@Validated @RequestBody BookingDTO bookingDTO  ){
		try {
			return ResponseEntity.ok(new MessageDTO<Object>(bookingService.save(bookingDTO)));
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO<Object>(ex.getMessage() ));
		}
	}
	
	@PutMapping
	public ResponseEntity<MessageDTO<Object>> update(@Validated @RequestBody BookingDTO bookingDTO){
		try {
			return ResponseEntity.ok(new MessageDTO<Object>(bookingService.update(bookingDTO)));
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO<Object>(ex.getMessage() ));
		}
	}
	
	@DeleteMapping(value = "{id}")
	public ResponseEntity<MessageDTO<String>> delete(@PathVariable("id") Integer id){
		try {
			bookingService.delete(id);
			return ResponseEntity.ok(new MessageDTO<String>("booking deleted"));
		} catch (InvalidEntityException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO<String>(ex.getMessage() ));
		}
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
		Map<String, String> errors = ex.getBindingResult().getAllErrors()
										.stream()
										.collect(Collectors.toMap(
													(k) -> ((FieldError) k).getField()
													, (v) -> v.getDefaultMessage()
												));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
	}
	
	
    private ResponseEntity<String> onError(HttpServerErrorException thr){
        return ResponseEntity.status(HttpStatus.OK).body("limit reached");
    }
	
}
