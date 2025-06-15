package org.travel_agency.exceptions;

public class InvalidEntityException extends Exception {

	private static final long serialVersionUID = -2867989676030347712L;
	private final Object entity;
	
	public InvalidEntityException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return this.entity;
	}

}
