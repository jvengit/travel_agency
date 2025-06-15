package org.travel_agency.exceptions;

public class EntityNotFoundException extends Exception {

	private static final long serialVersionUID = 3689551011979517891L;
	private final Object entity;
	
	public EntityNotFoundException(Object entity, String message) {
		super(message);
		this.entity = entity;
	}
	
	public Object getEntity() {
		return this.entity;
	}

}
