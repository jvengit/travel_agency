package org.hotel.entity;


import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
//import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "booking")
public class Booking {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
//		@SequenceGenerator(name = "my_generator", sequenceName = "HOTEL.HOTEL_PK_SEQUENCE",  allocationSize = 1)
		private Integer id;
		@Column(length = 10)
		private String name;
		@Column(length = 10)
		private String category;
		@Column(length = 10)
		private String roomType;
		@Column(length = 10)
		private String serviceType;
		@Column()
		private Long pricePerNight;
		@Column()
		private Integer status;
		
		public Booking() {
			super();
		}
		
		public Booking(Integer id, String name, String category, String roomType, String serviceType, Long pricePerNight, Integer status) {
			super();
			this.id = id;
			this.name = name;
			this.category = category;
			this.roomType = roomType;
			this.serviceType = serviceType;
			this.pricePerNight = pricePerNight;
			this.status = status;
		}
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getRoomType() {
			return roomType;
		}
		public void setRoomType(String roomType) {
			this.roomType = roomType;
		}
		public String getServiceType() {
			return serviceType;
		}
		public void setServiceType(String serviceType) {
			this.serviceType = serviceType;
		}
		public Long getPricePerNight() {
			return pricePerNight;
		}
		public void setPricePerNight(Long pricePerNight) {
			this.pricePerNight = pricePerNight;
		}
		public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		
		@Override
		public int hashCode() {
			return Objects.hash(category, id, name, pricePerNight, roomType, serviceType, status);
		}

		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Booking other = (Booking) obj;
			return Objects.equals(category, other.category) && Objects.equals(id, other.id)
					&& Objects.equals(name, other.name) && Objects.equals(pricePerNight, other.pricePerNight)
					&& Objects.equals(roomType, other.roomType) && Objects.equals(serviceType, other.serviceType)
					&& Objects.equals(status, other.status);
		}

		@Override
		public String toString() {
			return "Booking [id=" + id + ", name=" + name + ", category=" + category + ", roomType=" + roomType
					+ ", serviceType=" + serviceType + ", pricePerNight=" + pricePerNight + ", status=" + status + "]";
		}
		
}
