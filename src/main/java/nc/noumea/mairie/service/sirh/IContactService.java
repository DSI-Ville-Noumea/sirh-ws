package nc.noumea.mairie.service.sirh;

import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Contact;

public interface IContactService {

	public List<Contact> getContactsAgent(Long id);

	public List<Contact> getContactsDiffusableAgent(Long id);
}
