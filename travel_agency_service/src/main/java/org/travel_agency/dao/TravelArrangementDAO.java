package org.travel_agency.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.travel_agency.dto.TravelArrangementDTO;
import org.travel_agency.entity.TravelArrangement;

@Repository
public interface TravelArrangementDAO extends JpaRepository<TravelArrangement, Integer> {
	@Query("SELECT new org.travel_agency.dto.TravelArrangementDTO(t)"
			+ "FROM TravelArrangement t")
	List<TravelArrangementDTO> findAllTravelArrangements();
	
	@Query("SELECT new org.travel_agency.dto.TravelArrangementDTO(t) "
			+ "from TravelArrangement t "
			+ "WHERE t.id = :id")
	Optional<TravelArrangementDTO> findTravelArrangementById(@Param("id")Integer id);
}
