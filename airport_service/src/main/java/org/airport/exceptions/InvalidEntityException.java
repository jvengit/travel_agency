package org.airport.exceptions;

public class InvalidEntityException extends Exception {

	private static final long serialVersionUID = 3131582666241489246L;
	private final Object entity;

	public InvalidEntityException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
	
}
