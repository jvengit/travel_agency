package org.airport.exceptions;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 3178703958310274790L;
	private final Object entity;

	public EntityNotFoundException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
	
}
