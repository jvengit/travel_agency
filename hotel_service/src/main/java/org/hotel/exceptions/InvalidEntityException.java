package org.hotel.exceptions;

public class InvalidEntityException extends Exception {

	private static final long serialVersionUID = -5132594205432693111L;
	private final Object entity;

	public InvalidEntityException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
	
}
