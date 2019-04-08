package br.com.bravi.contactList.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.bravi.contactList.model.Person;

public interface PersonRepository extends JpaRepository<Person, Long> {

	
	
}
