package org.hotel.dto;

public record MessageDTO<T>(T dto) {

}


//public class MessageDTO<T>{
//	
//	private final T dto;
//
//	public MessageDTO(T dto) {
//		super();
//		this.dto = dto;
//	}
//
////	public MessageDTO() {
////		super();
////	}
//
//	public T getDto() {
//		return dto;
//	}
//	
//	
//}