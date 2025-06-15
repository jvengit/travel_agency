package org.hotel.service;


import java.util.List;
import java.util.Optional;

import org.hotel.dao.BookingDao;
import org.hotel.dto.BookingDTO;
import org.hotel.entity.Booking;
import org.hotel.exceptions.EntityExistsException;
import org.hotel.exceptions.EntityNotFoundException;
import org.hotel.exceptions.InvalidEntityException;
import org.hotel.mappers.BookingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

import org.mapstruct.factory.Mappers;


@Service
public class BookingService {
	
	final static Logger logger = LoggerFactory.getLogger(BookingService.class);
	private final BookingDao bookingDao;
	private final BookingMapper bookingMapper = Mappers.getMapper(BookingMapper.class);
    private ObservationRegistry observationRegistry;
    
	public BookingService(BookingDao bookingDao, ObservationRegistry observationRegistry) {
		super();
		this.bookingDao = bookingDao;
		this.observationRegistry = observationRegistry;
	}

	public List<BookingDTO> findAllBookings() {
		List<BookingDTO> bookings = null;
		Observation observation = Observation.start("Myflights3", this.observationRegistry);
		try (Observation.Scope ignored = observation.openScope()){
			bookings = bookingDao.findAllBookings();
			observation.event(Observation.Event.of("flightsEvent3", "List of all flights3"));
		} catch(Exception ex) {
			observation.error(ex);
		}finally {
			observation.stop();
		}
		return bookings;
		
//		List<BookingDTO> bookings = bookingDao.findAllBookings();
//		return bookings;
	}
	
	public Optional<BookingDTO> findBookingById(Integer id) {
		Optional<BookingDTO> booking = bookingDao.findBookingById(id);
		if(booking.isPresent()) {
			return booking;
		}
		return Optional.empty();
	}
	
	@Transactional
	public BookingDTO save(BookingDTO bookingDTO) throws EntityExistsException {
		return Optional.of(bookingDTO)
					.map( (b) -> bookingMapper.toDto(bookingDao.save(bookingMapper.toEntity(b))) )
					.orElseThrow(() -> {return new EntityExistsException(bookingDTO, "Booking exists");});	
	}

	@Transactional
	public BookingDTO update(BookingDTO bookingDTO) throws EntityNotFoundException {
		if(bookingDao.findBookingById(bookingDTO.id()).isEmpty())
			throw new EntityNotFoundException(bookingDTO, "Booking does not exists");
		else {
			logger.info(bookingMapper.toEntity(bookingDTO).toString());
			Booking booking = bookingDao.save(bookingMapper.toEntity(bookingDTO));
			return bookingMapper.toDto(booking);
		}
	}

	@Transactional
	public void delete(Integer id) throws InvalidEntityException{
		Optional<Booking> booking = bookingDao.findById(id);
		if(booking.isEmpty())
			throw new InvalidEntityException(id, "Invalid booking");
		else {
			booking.orElseGet(null).setStatus(3);
		}
	}
}
