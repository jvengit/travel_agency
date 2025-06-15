package org.hotel.exceptions;

public class EntityExistsException extends Exception {
	
	private static final long serialVersionUID = 2701056025213368832L;
	private final Object entity;

	public EntityExistsException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
}