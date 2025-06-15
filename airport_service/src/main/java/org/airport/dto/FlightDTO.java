package org.airport.dto;

import java.time.LocalDateTime;
import org.airport.entity.Flight;

import com.fasterxml.jackson.annotation.JsonFormat;


public record FlightDTO(Integer id, String gate, String airline, String destination, String origin, String flight, Integer status
,@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime departure
,@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime arrival) {
	
	FlightDTO(Flight a){
		this(a.getId(),a.getGate() ,a.getAirline() ,a.getDestination() ,a.getOrigin() ,a.getFlight() ,a.getStatus() ,a.getDeparture() ,a.getArrival() );
	}
	
}
