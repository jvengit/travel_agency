package org.airport.entity;

import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "flight")
public class Flight {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 10)
	private String gate;
	@Column(length = 10)
	private String airline;
	@Column(length = 10)
	private String destination;
	@Column(length = 10)
	private String origin;
	@Column(length = 10)
	private String flight;
	@Column()
	private Integer status;
	@Column
	private LocalDateTime departure;
	@Column
	private LocalDateTime arrival;
	
	public Flight() {
		super();
	}

	public Flight(Integer id, String gate, String airline, String destination, String origin, String flight,
			Integer status, LocalDateTime departure, LocalDateTime arrival) {
		super();
		this.id = id;
		this.gate = gate;
		this.airline = airline;
		this.destination = destination;
		this.origin = origin;
		this.flight = flight;
		this.status = status;
		this.departure = departure;
		this.arrival = arrival;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGate() {
		return gate;
	}

	public void setGate(String gate) {
		this.gate = gate;
	}

	public String getAirline() {
		return airline;
	}

	public void setAirline(String airline) {
		this.airline = airline;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getFlight() {
		return flight;
	}

	public void setFlight(String flight) {
		this.flight = flight;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getDeparture() {
		return departure;
	}

	public void setDeparture(LocalDateTime departure) {
		this.departure = departure;
	}

	public LocalDateTime getArrival() {
		return arrival;
	}

	public void setArrival(LocalDateTime arrival) {
		this.arrival = arrival;
	}

	@Override
	public int hashCode() {
		return Objects.hash(airline, arrival, departure, destination, flight, gate, id, origin, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Flight other = (Flight) obj;
		return Objects.equals(airline, other.airline) && Objects.equals(arrival, other.arrival)
				&& Objects.equals(departure, other.departure) && Objects.equals(destination, other.destination)
				&& Objects.equals(flight, other.flight) && Objects.equals(gate, other.gate) && Objects.equals(id, other.id)
				&& Objects.equals(origin, other.origin) && Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "Flight [id=" + id + ", gate=" + gate + ", airline=" + airline + ", destination=" + destination
				+ ", origin=" + origin + ", flight=" + flight + ", status=" + status + ", departure=" + departure
				+ ", arrival=" + arrival + "]";
	}
	
	
	
}
