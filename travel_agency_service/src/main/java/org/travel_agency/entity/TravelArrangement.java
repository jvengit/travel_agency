package org.travel_agency.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "travel_arrangement")
public class TravelArrangement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 10)
	private String agent;
	@Column(length = 10)
	private String client;
	@Column(name = "FLIGHT_ID")
	private Integer flightId;
	@Column(name = "BOOKING_ID")
	private Integer bookingId;
	private Integer status;
	
	public TravelArrangement() {
		super();
	}
	
	public TravelArrangement(Integer id, String name, String agent, String client, Integer flightId,
			Integer bookingId, Integer status) {
		super();
		this.id = id;
		this.agent = agent;
		this.client = client;
		this.flightId = flightId;
		this.bookingId = bookingId;
		this.status = status;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public Integer getFlightId() {
		return flightId;
	}

	public void setFlightId(Integer flightId) {
		this.flightId = flightId;
	}

	public Integer getBookingId() {
		return bookingId;
	}

	public void setBookingId(Integer bookingId) {
		this.bookingId = bookingId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(agent, bookingId, client, flightId, id, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TravelArrangement other = (TravelArrangement) obj;
		return Objects.equals(agent, other.agent) && Objects.equals(bookingId, other.bookingId)
				&& Objects.equals(client, other.client) && Objects.equals(flightId, other.flightId)
				&& Objects.equals(id, other.id) && Objects.equals(status, other.status);
	}

	@Override
	public String toString() {
		return "TravelArrangement [id=" + id + ", agent=" + agent + ", client=" + client
				+ ", flightId=" + flightId + ", bookingId=" + bookingId + ", status=" + status + "]";
	}

	
	
	
}
