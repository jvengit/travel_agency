package org.hotel.dao;

import java.util.List;
import java.util.Optional;

import org.hotel.dto.BookingDTO;
import org.hotel.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingDao extends JpaRepository<Booking, Integer> {
	
	@Query("SELECT new org.hotel.dto.BookingDTO(b)"
			+ "FROM Booking b")
	List<BookingDTO> findAllBookings();
	
	@Query("SELECT new org.hotel.dto.BookingDTO(b) "
			+ "from Booking b "
			+ "WHERE b.id = :id")
	Optional<BookingDTO> findBookingById(@Param("id")Integer id);
}
