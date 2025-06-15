package org.travel_agency.dto;

import org.travel_agency.entity.TravelArrangement;

public record TravelArrangementDTO(Integer id, String agent, String client, Integer flightId, Integer bookingId, Integer status) {
	public TravelArrangementDTO(TravelArrangement t) {
		this(t.getId(), t.getAgent(), t.getClient(), t.getFlightId(), t.getBookingId(), t.getStatus());
	}
}
