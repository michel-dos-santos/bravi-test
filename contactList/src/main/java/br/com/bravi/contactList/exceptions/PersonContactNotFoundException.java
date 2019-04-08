package br.com.bravi.contactList.exceptions;

public class PersonContactNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersonContactNotFoundException(Long idPerson, Long idContact) {
		super("Could not find person = " + idPerson + " and/or contact = " + idContact);
	}
	
}
