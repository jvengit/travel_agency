package org.airport.service;


import java.util.List;
import java.util.Optional;

import org.airport.dao.FlightDao;
import org.airport.dto.FlightDTO;
import org.airport.dto.IFlightDTO;
import org.airport.entity.Flight;
import org.airport.exceptions.EntityExistsException;
import org.airport.exceptions.EntityNotFoundException;
import org.airport.exceptions.InvalidEntityException;
import org.airport.mappers.FlightMapper;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.micrometer.observation.Observation;
import io.micrometer.observation.ObservationRegistry;

@Service
public class FlightService {
	
	final static Logger logger = LoggerFactory.getLogger(FlightService.class);
	private final FlightMapper flightMapper = Mappers.getMapper(FlightMapper.class);
	private final FlightDao flightDao;
    private ObservationRegistry observationRegistry;
	
	public FlightService(FlightDao flightDao, ObservationRegistry observationRegistry) {
		super();
		this.flightDao = flightDao;
		this.observationRegistry = observationRegistry;
	}
	
	@Transactional
	public List<FlightDTO> findAllFlights() {
		List<FlightDTO> flights = null;
  
		Observation observation = Observation.createNotStarted("Myflights", this.observationRegistry);
        observation.start();
		try (Observation.Scope ignored = observation.openScope()){
			flights = flightDao.findAllFlights();
			flightDao.printStatistics();
			observation.event(Observation.Event.of("flightsEvent3", "List of all flights3"));
		} catch(Exception ex) {
			observation.error(ex);
		}finally {
			observation.stop();
		}
		return flights;
	}
	
	@Transactional(readOnly = true)
	public List<IFlightDTO> findAllFlightsni() {
		List<IFlightDTO> flights = flightDao.findAllFlightsni();
		flightDao.printStatistics();
		return flights;
	}
	
	@Transactional(readOnly = true)
	public List<Flight> findAllFlightsne() {
		List<Flight> flights = flightDao.findAllFlightsne();
		flightDao.printStatistics();
		return flights;
	}
	
	@Transactional
	public Optional<FlightDTO> findFlightById(Integer id) {
		Optional<FlightDTO> flight = flightDao.findFlightById(id);
		flightDao.printStatistics();
		if(flight.isPresent()) {
			return flight;
		}
		return Optional.empty();
	}
	
	@Transactional
	public FlightDTO save(FlightDTO flightDTO) throws EntityExistsException {
		return Optional.of(flightDTO)
				.map( (b) -> flightMapper.toDto(flightDao.save(flightMapper.toEntity(b))) )
				.orElseThrow(() -> {return new EntityExistsException(flightDTO, "Flight exists");});	
//		if(flightDao.findFlightById(flightDTO.id()).isPresent())
//			throw new EntityExistsException(flightDTO, "Flight exists");
//		else {
//			Flight flight = flightDao.save(flightMapper.toEntity(flightDTO));
//			return flightMapper.toDto(flight);
//		}		
	}
	
	@Transactional
	public FlightDTO update(FlightDTO flightDTO) throws EntityNotFoundException {
		if(flightDao.findFlightById(flightDTO.id()).isEmpty())
			throw new EntityNotFoundException(flightDTO, "flight does not exists");
		else {
			logger.info(flightMapper.toEntity(flightDTO).toString());
			Flight flight = flightDao.save(flightMapper.toEntity(flightDTO));
			return flightMapper.toDto(flight);
		}
	}
	
	@Transactional
	public void delete(Integer id) throws InvalidEntityException {
		Optional<Flight> hotel = flightDao.findById(id);
		if(hotel.isEmpty())
			throw new InvalidEntityException(id, "invalid flight");
		else {
			hotel.orElseGet(null).setStatus(3);
		}
	}
	
}
