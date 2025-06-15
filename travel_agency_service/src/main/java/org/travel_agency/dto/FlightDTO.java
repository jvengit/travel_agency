package org.travel_agency.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public record FlightDTO(Integer id, String gate, String airline, String destination, String origin, String flight, Integer status
,@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime departure
,@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss") LocalDateTime arrival) {
		
}
