package org.hotel.dto;

import org.hotel.entity.Booking;
import jakarta.validation.constraints.Size;

public record BookingDTO(Integer id, @Size(min = 2, max = 10)String name, String roomType, String category, String serviceType, Long pricePerNight, Integer status) {
	public BookingDTO(Booking b) {
		this(b.getId(), b.getName(), b.getRoomType(), b.getCategory(), b.getServiceType(), b.getPricePerNight(), b.getStatus());
	}
}

//public interface HotelDTO{
//	
//	Integer getId();
//	String getName();
//	String getRoomType();
//	String getCategory();
//	String getServiceType();
//	Long getPricePerNight();
//	
//}
