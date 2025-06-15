package org.travel_agency.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.travel_agency.dto.TravelArrangementDTO;
import org.travel_agency.entity.TravelArrangement;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL)
public interface TravelArrangementMapper {
	TravelArrangement toEntity(TravelArrangementDTO travelArrangementDTO);
	TravelArrangementDTO toDto(TravelArrangement travelArrangement);
}
