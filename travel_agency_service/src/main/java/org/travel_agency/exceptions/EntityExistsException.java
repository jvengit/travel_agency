package org.travel_agency.exceptions;

public class EntityExistsException extends Exception {

	private static final long serialVersionUID = 4914409728079613128L;
	private final Object entity;
	
	public EntityExistsException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return this.entity;
	}

}
