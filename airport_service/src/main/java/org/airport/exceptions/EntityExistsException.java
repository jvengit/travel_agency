package org.airport.exceptions;

public class EntityExistsException extends Exception {
	
	private static final long serialVersionUID = -1203191757226790704L;
	private final Object entity;

	public EntityExistsException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
}
