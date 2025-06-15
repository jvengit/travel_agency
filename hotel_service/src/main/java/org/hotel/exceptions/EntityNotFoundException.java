package org.hotel.exceptions;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = -1784446444894633112L;
	private final Object entity;

	public EntityNotFoundException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return entity;
	}
	
}
