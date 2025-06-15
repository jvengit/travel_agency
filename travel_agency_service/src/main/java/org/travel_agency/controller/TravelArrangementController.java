package org.travel_agency.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
import org.travel_agency.dto.BookingDTO;
import org.travel_agency.dto.FlightDTO;
import org.travel_agency.dto.MessageDTO;
import org.travel_agency.dto.TravelArrangementDTO;
import org.travel_agency.exceptions.EntityExistsException;
import org.travel_agency.exceptions.EntityNotFoundException;
import org.travel_agency.exceptions.InvalidEntityException;
import org.travel_agency.service.TravelArrangementService;

@RestController
@RequestMapping("arrangement")
public class TravelArrangementController {

	private final TravelArrangementService travelArrangementService;
	
	public TravelArrangementController(TravelArrangementService travelArrangementService) {
		this.travelArrangementService = travelArrangementService;
	}
	
	@GetMapping("/all")
	public ResponseEntity<MessageDTO<List<TravelArrangementDTO>>> findAllTravelArrangements(){
		return ResponseEntity.ok(new MessageDTO<List<TravelArrangementDTO>>(travelArrangementService.findAllTravelArrangements()) );
	}
	
	@GetMapping("{id}")
	public ResponseEntity<MessageDTO<TravelArrangementDTO>> findAllTravelArrangements(@PathVariable(value = "id") Integer id){
		return travelArrangementService.findTravelArrangementById(id)
									.map( (k) -> ResponseEntity.ok(new MessageDTO<TravelArrangementDTO>(k)) )
									.orElseGet( () -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageDTO<TravelArrangementDTO>(null)) )
								;
	}
	
	@GetMapping("bookings")
	public ResponseEntity<MessageDTO<List<BookingDTO>>> findAllBookings(){
		return ResponseEntity.ok(new MessageDTO<List<BookingDTO>>(travelArrangementService.findAllBookings())) ;
//		return travelArrangementService.getBooking().dto().id();
	}
	
	@GetMapping("flights")
	public ResponseEntity<MessageDTO<List<FlightDTO>>> findAllFlights(){
		return ResponseEntity.ok(new MessageDTO<List<FlightDTO>>(travelArrangementService.findAllFlights())) ;
	}
	
	@GetMapping("flightDetail")
	public ResponseEntity<MessageDTO<String>> findFlightDetail(){
		return ResponseEntity.ok(new MessageDTO<String>(travelArrangementService.findFlightDetails())) ;
	}
	
	@PostMapping
	public ResponseEntity<MessageDTO<Object>> save(@Validated @RequestBody TravelArrangementDTO travelArrangementDTO){
		try {
			return ResponseEntity.ok(new MessageDTO<Object>(travelArrangementService.save(travelArrangementDTO)));
		} catch (EntityExistsException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO<Object>(ex.getMessage()));
		}
	}
	
	@PutMapping
	public ResponseEntity<MessageDTO<Object>> update(@Validated @RequestBody TravelArrangementDTO travelArrangementDTO){
		try {
			return ResponseEntity.ok(new MessageDTO<Object>(travelArrangementService.update(travelArrangementDTO)));
		} catch (EntityNotFoundException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO<Object>(ex.getMessage()));
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<MessageDTO<String>> delete(@PathVariable(value = "id") Integer id){
		try {
			travelArrangementService.delete(id);
			return ResponseEntity.ok(new MessageDTO<String>("Travel arrangement deleted") );
		} catch (InvalidEntityException ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageDTO<String>(ex.getMessage()) );
		}
	}
	
	@ExceptionHandler({MethodArgumentNotValidException.class})
	public Map<String, String> handleValidationErrors(MethodArgumentNotValidException ex){
		return ex.getAllErrors().stream()
							.collect(Collectors.toMap(
									(k) -> ((FieldError) k).getField()
									, (v) -> v.getDefaultMessage())
							);
	}
	
}
