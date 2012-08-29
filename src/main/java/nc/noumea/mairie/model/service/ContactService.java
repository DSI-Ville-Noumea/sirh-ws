package nc.noumea.mairie.model.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Contact;

import org.springframework.stereotype.Service;

@Service
public class ContactService implements IContactService {

	@PersistenceContext
	transient EntityManager entityManager;

	@Override
	public List<Contact> getContactsAgent(Long id) {
		Query query = entityManager.createQuery(
				"select contact from Contact contact "
						+ "where contact.idAgent=:idAgent", Contact.class);
		query.setParameter("idAgent", id.intValue());
		List<Contact> lc = query.getResultList();

		return lc;
	}

}
