package br.com.bravi.contactList.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.bravi.contactList.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	@Query("select c from Contact c where c.id = ?1 and c.person.id = ?2")
	Contact findContact(Long id, Long person_id);
	
}
