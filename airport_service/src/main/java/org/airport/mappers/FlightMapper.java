package org.airport.mappers;

import org.airport.dto.FlightDTO;
import org.airport.entity.Flight;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface FlightMapper {
	Flight toEntity(FlightDTO flightDto);
	FlightDTO toDto(Flight flight);
}
