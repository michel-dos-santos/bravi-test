package br.com.bravi.contactList.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bravi.contactList.exceptions.PersonContactNotFoundException;
import br.com.bravi.contactList.exceptions.PersonNotFoundException;
import br.com.bravi.contactList.model.Contact;
import br.com.bravi.contactList.model.Person;
import br.com.bravi.contactList.repository.ContactRepository;
import br.com.bravi.contactList.repository.PersonRepository;

@RestController
public class ContactController {

	@Autowired
	private ContactRepository contactRepository;
	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/people/{idPerson}/contacts")
	public List<Contact> all(@PathVariable Long idPerson){
		Person person = personRepository.findById(idPerson).orElseThrow(() -> new PersonNotFoundException(idPerson));
		return person.getContacts();
	}
	
	@GetMapping("/people/{idPerson}/contacts/{idContact}")
	public Contact one(@PathVariable Long idPerson, @PathVariable Long idContact) {
		Contact contact = contactRepository.findContact(idContact, idPerson);
		if(contact == null) {
			throw new PersonContactNotFoundException(idPerson, idContact);
		}
		return contact;
	}
	
	@PostMapping("/people/{idPerson}/contacts")
	public List<Contact> newPerson(@PathVariable Long idPerson, @RequestBody List<Contact> contacts) { 
		Person person = personRepository.findById(idPerson).orElseThrow(() -> new PersonNotFoundException(idPerson));
		contacts.forEach(contact -> contact.setPerson(person));
		person.getContacts().addAll(contacts);
		return personRepository.save(person).getContacts();
	}
	
	@PutMapping("/people/{idPerson}/contacts/{idContact}")
	public Contact replaceContact(@RequestBody Contact newContact, @PathVariable Long idPerson, @PathVariable Long idContact) {
		Person person = personRepository.findById(idPerson).orElseThrow(() -> new PersonNotFoundException(idPerson));
		return person.getContacts().stream().filter(contact -> contact.equals(new Contact(idContact))).findFirst().map(contact -> {
			contact.setContactType(newContact.getContactType());
			contact.setEmail(newContact.getEmail());
			contact.setPerson(person);
			contact.setPhone(newContact.getPhone());
			contact.setWhatsapp(newContact.getWhatsapp());
			
			return contactRepository.save(contact);
		}).orElseGet(() -> {
			newContact.setPerson(person);
			return contactRepository.save(newContact);
		});
	}
	
	@DeleteMapping("/people/{idPerson}/contacts/{idContact}") 
	public void deleteContact(@PathVariable Long idPerson, @PathVariable Long idContact) {
		Contact contact = contactRepository.findContact(idContact, idPerson);
		if(contact == null) {
			throw new PersonContactNotFoundException(idPerson, idContact);
		}
		contactRepository.deleteById(idContact);
	}
	
}
