package org.airport.dao;

import java.util.List;
import java.util.Optional;

import org.airport.dto.FlightDTO;
import org.airport.dto.IFlightDTO;
import org.airport.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightDao extends JpaRepository<Flight, Integer>, CustomRepo {
	
	@Query(value = "SELECT * from flight ", nativeQuery = true)
	List<Flight> findAllFlightsne();
	
	@Query("SELECT new org.airport.dto.FlightDTO(f)"
	+ "FROM Flight f")
	List<FlightDTO> findAllFlights();
	
//	@Query("SELECT new org.airport.dto.FlightDTO(f)"
//			+ "FROM Flight f")
	@Query(value = "SELECT id, gate, destination, airline, origin from flight", nativeQuery = true)
	List<IFlightDTO> findAllFlightsni();
	
	@Query("SELECT new org.airport.dto.FlightDTO(f) "
			+ "from Flight f "
			+ "WHERE f.id = :id")
	Optional<FlightDTO> findFlightById(@Param("id")Integer id);
}
