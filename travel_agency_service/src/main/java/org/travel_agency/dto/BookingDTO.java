package org.travel_agency.dto;

public record BookingDTO(Integer id, String name, String roomType, String category, String serviceType, Long pricePerNight, Integer status) {

}
