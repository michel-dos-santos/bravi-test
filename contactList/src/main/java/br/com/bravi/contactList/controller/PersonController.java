package br.com.bravi.contactList.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.bravi.contactList.exceptions.PersonNotFoundException;
import br.com.bravi.contactList.model.Person;
import br.com.bravi.contactList.repository.PersonRepository;

@RestController
public class PersonController {

	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping("/")
	public List<String> services(){
		return Arrays.asList(
				"Get all People => Method GET => http://localhost:8080/people => Body is Empty", 
				"Get one Person => Method GET => http://localhost:8080/people/{idPerson} => Body is Empty",
				"Create one Person => Method POST => http://localhost:8080/people => Body contains all personal information including contact information",
				"Update one Person => Method PUT => http://localhost:8080/people/{idPerson} => Body contains only personal information except contact information",
				"Delete one Person => Method DELETE => http://localhost:8080/people/{idPerson} => Body is Empty", 
				
				
				"Get all Contacts of a person => Method GET => http://localhost:8080/people/{idPerson}/contacts => Body is Empty", 
				"Get one Contact of a person => Method GET => http://localhost:8080/people/{idPerson}/contacts/{idContact} => Body is Empty",
				"Create one Contact of a person => Method POST => http://localhost:8080/people/{idPerson}/contacts => Body contains only contact information",
				"Update one Contact of a person => Method PUT => http://localhost:8080/people/{idPerson}/contacts/{idContact} => Body contains only contact information",
				"Delete one Contact of a person => Method DELETE => http://localhost:8080/people/{idPerson}/contacts/{idContact} => Body is Empty");
	}
	
	@GetMapping("/people")
	public List<Person> all() {
		return personRepository.findAll();
	}
	
	@GetMapping("/people/{id}")
	public Person one(@PathVariable Long id) {
		return personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
	}
	
	@PostMapping("/people")
	public Person newPerson(@RequestBody Person person) { 
		if(person.getContacts() != null) {
			person.getContacts().forEach(contact -> contact.setPerson(person));
		}
		return personRepository.save(person);
	}
	
	@PutMapping("/people/{id}")
	public Person replacePerson(@RequestBody Person newPerson, @PathVariable Long id) {
		return personRepository.findById(id).map(person -> {
			person.setFirstName(newPerson.getFirstName());
			person.setLastName(newPerson.getLastName());
			person.setNickName(newPerson.getNickName());
			person.setBirthDay(newPerson.getBirthDay());
			person.setJob(newPerson.getJob());
			
			return personRepository.save(person);
		}).orElseGet(() -> {
			newPerson.getContacts().forEach(contact -> contact.setPerson(newPerson));
			return personRepository.save(newPerson);
		});
	}
	
	@DeleteMapping("/people/{id}") 
	public void deletePerson(@PathVariable Long id) {
		personRepository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
		personRepository.deleteById(id);
	}
}
