package br.com.bravi.contactList.exceptions;

public class PersonNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PersonNotFoundException(Long id) {
		super("Could not find person = " + id);
	}
	
}
