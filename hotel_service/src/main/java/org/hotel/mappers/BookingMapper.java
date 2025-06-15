package org.hotel.mappers;

import org.hotel.dto.BookingDTO;
import org.hotel.entity.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface BookingMapper {
	Booking toEntity(BookingDTO bookingDto);
	BookingDTO toDto(Booking booking);
}
