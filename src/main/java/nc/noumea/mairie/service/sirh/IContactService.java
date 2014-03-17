package nc.noumea.mairie.service.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.Contact;

public interface IContactService {

	public List<Contact> getContactsAgent(Long id);
}
