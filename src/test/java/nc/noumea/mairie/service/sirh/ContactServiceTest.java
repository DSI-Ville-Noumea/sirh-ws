package nc.noumea.mairie.service.sirh;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.sirh.Contact;
import nc.noumea.mairie.service.sirh.ContactService;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class ContactServiceTest {

	@Test
	public void getContactsAgent_returnEmptyList() {
		// Given
		List<Contact> listeContact = new ArrayList<Contact>();

		Siserv service = new Siserv();
		service.setServi("N");
		service.setLiServ("NONO");

		@SuppressWarnings("unchecked")
		TypedQuery<Contact> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeContact);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Contact.class))).thenReturn(mockQuery);

		ContactService contactService = new ContactService();
		ReflectionTestUtils.setField(contactService, "sirhEntityManager", sirhEMMock);

		// When
		List<Contact> result = contactService.getContactsAgent((long) 9005138);

		// Then
		assertEquals(0, result.size());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void getContactsAgent_returnListContact() {
		// Given
		List<Contact> listeContact = new ArrayList<Contact>();
		Contact ag1 = new Contact();
		ag1.setIdAgent(9005138);
		Contact ag2 = new Contact();
		ag2.setIdAgent(9005131);
		ag2.setDescription("TEST DESC");
		listeContact.add(ag1);
		listeContact.add(ag2);

		Siserv service = new Siserv();
		service.setServi("N");
		service.setLiServ("NONO");

		TypedQuery<Contact> mockQuery = Mockito.mock(TypedQuery.class);
		Mockito.when(mockQuery.getResultList()).thenReturn(listeContact);

		EntityManager sirhEMMock = Mockito.mock(EntityManager.class);
		Mockito.when(sirhEMMock.createQuery(Mockito.anyString(), Mockito.eq(Contact.class))).thenReturn(mockQuery);

		ContactService contactService = new ContactService();
		ReflectionTestUtils.setField(contactService, "sirhEntityManager", sirhEMMock);

		// When
		List<Contact> result = contactService.getContactsAgent((long) 9005138);

		// Then
		assertEquals(2, result.size());
		assertEquals(ag1.getIdAgent(), result.get(0).getIdAgent());
		assertEquals(ag2.getIdAgent(), result.get(1).getIdAgent());
		assertEquals("TEST DESC", result.get(1).getDescription());
	}
}