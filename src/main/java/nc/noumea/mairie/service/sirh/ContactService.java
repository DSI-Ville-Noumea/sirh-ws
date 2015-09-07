package nc.noumea.mairie.service.sirh;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.sirh.Contact;

import org.springframework.stereotype.Service;

@Service
public class ContactService implements IContactService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public List<Contact> getContactsAgent(Long id) {
		TypedQuery<Contact> query = sirhEntityManager.createQuery(
				"select contact from Contact contact where contact.idAgent=:idAgent", Contact.class);
		query.setParameter("idAgent", id.intValue());
		List<Contact> lc = query.getResultList();

		return lc;
	}

	@Override
	public List<Contact> getContactsDiffusableAgent(Long id) {
		TypedQuery<Contact> query = sirhEntityManager.createQuery(
				"select contact from Contact contact where contact.diffusable = '1' and contact.idAgent=:idAgent",
				Contact.class);
		query.setParameter("idAgent", id.intValue());
		List<Contact> lc = query.getResultList();

		return lc;
	}

}
