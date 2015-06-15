package nc.noumea.mairie.model.repository.sirh;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import nc.noumea.mairie.model.bean.sirh.Droits;
import nc.noumea.mairie.model.bean.sirh.DroitsElement;
import nc.noumea.mairie.model.bean.sirh.DroitsGroupe;
import nc.noumea.mairie.model.bean.sirh.TypeDroitEnum;
import nc.noumea.mairie.model.bean.sirh.Utilisateur;
import nc.noumea.mairie.model.pk.sirh.DroitsId;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/META-INF/spring/applicationContext-test.xml" })
public class DroitsRepositoryTest {

	@Autowired
	IDroitsRepository repository;

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhPersistenceUnit;
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getDroitsByElementAndAgent_return1result() {
		
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setIdUtilisateur(1);
		utilisateur.setLogin("rebjo84");
		sirhPersistenceUnit.persist(utilisateur);
		
		Set<Utilisateur> utilisateurs = new HashSet<Utilisateur>();
		utilisateurs.add(utilisateur);
		
		DroitsElement element = new DroitsElement();
		element.setIdElement(86);
		element.setLibElement("libElement");
		sirhPersistenceUnit.persist(element);
		
		DroitsGroupe groupeUtilisateur = new DroitsGroupe();
		groupeUtilisateur.setIdGroupe(1);
		groupeUtilisateur.setLibGroupe("libGroupe");
		groupeUtilisateur.setUtilisateurs(utilisateurs);
		sirhPersistenceUnit.persist(groupeUtilisateur);
		
		DroitsId idDroit = new DroitsId();
		idDroit.setIdElement(element.getIdElement());
		idDroit.setIdGroupe(groupeUtilisateur.getIdGroupe());
		
		Droits droit = new Droits();
		droit.setId(idDroit);
		droit.setIdTypeDroit(TypeDroitEnum.CONSULTATION.getIdTypeDroit());
		droit.setElement(element);
		droit.setDroitsGroupe(groupeUtilisateur);
		sirhPersistenceUnit.persist(droit);
		
		Set<Droits> droits = new HashSet<Droits>();
		droits.add(droit);
		
		groupeUtilisateur.setDroits(droits);
		
		List<Droits> result = repository.getDroitsByElementAndAgent(86, "rebjo84");
		
		assertNotNull(result);
		assertEquals(result.get(0).getIdTypeDroit().intValue(), TypeDroitEnum.CONSULTATION.getIdTypeDroit().intValue());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getDroitsByElementAndAgent_return2results() {
		
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setIdUtilisateur(1);
		utilisateur.setLogin("rebjo84");
		sirhPersistenceUnit.persist(utilisateur);
		
		Set<Utilisateur> utilisateurs = new HashSet<Utilisateur>();
		utilisateurs.add(utilisateur);
		
		DroitsElement element = new DroitsElement();
		element.setIdElement(86);
		element.setLibElement("libElement");
		sirhPersistenceUnit.persist(element);
		
		DroitsGroupe groupeUtilisateur = new DroitsGroupe();
		groupeUtilisateur.setIdGroupe(1);
		groupeUtilisateur.setLibGroupe("libGroupe");
		groupeUtilisateur.setUtilisateurs(utilisateurs);
		sirhPersistenceUnit.persist(groupeUtilisateur);
		
		DroitsGroupe groupeUtilisateur2 = new DroitsGroupe();
		groupeUtilisateur2.setIdGroupe(2);
		groupeUtilisateur2.setLibGroupe("libGroupe2");
		groupeUtilisateur2.setUtilisateurs(utilisateurs);
		sirhPersistenceUnit.persist(groupeUtilisateur2);
		
		DroitsId idDroit = new DroitsId();
		idDroit.setIdElement(element.getIdElement());
		idDroit.setIdGroupe(groupeUtilisateur.getIdGroupe());
		
		Droits droit = new Droits();
		droit.setId(idDroit);
		droit.setIdTypeDroit(TypeDroitEnum.CONSULTATION.getIdTypeDroit());
		droit.setElement(element);
		droit.setDroitsGroupe(groupeUtilisateur);
		sirhPersistenceUnit.persist(droit);
		
		DroitsId idDroit2 = new DroitsId();
		idDroit2.setIdElement(element.getIdElement());
		idDroit2.setIdGroupe(groupeUtilisateur2.getIdGroupe());
		
		Droits droit2 = new Droits();
		droit2.setId(idDroit2);
		droit2.setIdTypeDroit(TypeDroitEnum.EDITION.getIdTypeDroit());
		droit2.setElement(element);
		droit2.setDroitsGroupe(groupeUtilisateur2);
		sirhPersistenceUnit.persist(droit2);
		
		Set<Droits> droits = new HashSet<Droits>();
		droits.add(droit);

		Set<Droits> droits2 = new HashSet<Droits>();
		droits2.add(droit2);
		
		groupeUtilisateur.setDroits(droits);
		groupeUtilisateur2.setDroits(droits2);
		
		List<Droits> result = repository.getDroitsByElementAndAgent(86, "rebjo84");
		
		assertEquals(result.get(0).getIdTypeDroit().intValue(), TypeDroitEnum.CONSULTATION.getIdTypeDroit().intValue());
		assertEquals(result.get(1).getIdTypeDroit().intValue(), TypeDroitEnum.EDITION.getIdTypeDroit().intValue());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getDroitsByElementAndAgent_rnoResult_becausebadLogin() {
		
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setIdUtilisateur(1);
		utilisateur.setLogin("rebjo84");
		sirhPersistenceUnit.persist(utilisateur);
		
		Set<Utilisateur> utilisateurs = new HashSet<Utilisateur>();
		utilisateurs.add(utilisateur);
		
		DroitsElement element = new DroitsElement();
		element.setIdElement(86);
		element.setLibElement("libElement");
		sirhPersistenceUnit.persist(element);
		
		DroitsGroupe groupeUtilisateur = new DroitsGroupe();
		groupeUtilisateur.setIdGroupe(1);
		groupeUtilisateur.setLibGroupe("libGroupe");
		groupeUtilisateur.setUtilisateurs(utilisateurs);
		sirhPersistenceUnit.persist(groupeUtilisateur);
		
		DroitsId idDroit = new DroitsId();
		idDroit.setIdElement(element.getIdElement());
		idDroit.setIdGroupe(groupeUtilisateur.getIdGroupe());
		
		Droits droit = new Droits();
		droit.setId(idDroit);
		droit.setIdTypeDroit(TypeDroitEnum.CONSULTATION.getIdTypeDroit());
		droit.setElement(element);
		droit.setDroitsGroupe(groupeUtilisateur);
		sirhPersistenceUnit.persist(droit);
		
		Set<Droits> droits = new HashSet<Droits>();
		droits.add(droit);
		
		groupeUtilisateur.setDroits(droits);
		
		List<Droits> result = repository.getDroitsByElementAndAgent(86, "nicno85");
		
		assertTrue(result.isEmpty());
	}
	
	@Test
	@Transactional("sirhTransactionManager")
	public void getDroitsByElementAndAgent_rnoResult_becausebadElement() {
		
		Utilisateur utilisateur = new Utilisateur();
		utilisateur.setIdUtilisateur(1);
		utilisateur.setLogin("rebjo84");
		sirhPersistenceUnit.persist(utilisateur);
		
		Set<Utilisateur> utilisateurs = new HashSet<Utilisateur>();
		utilisateurs.add(utilisateur);
		
		DroitsElement element = new DroitsElement();
		element.setIdElement(86);
		element.setLibElement("libElement");
		sirhPersistenceUnit.persist(element);
		
		DroitsGroupe groupeUtilisateur = new DroitsGroupe();
		groupeUtilisateur.setIdGroupe(1);
		groupeUtilisateur.setLibGroupe("libGroupe");
		groupeUtilisateur.setUtilisateurs(utilisateurs);
		sirhPersistenceUnit.persist(groupeUtilisateur);
		
		DroitsId idDroit = new DroitsId();
		idDroit.setIdElement(element.getIdElement());
		idDroit.setIdGroupe(groupeUtilisateur.getIdGroupe());
		
		Droits droit = new Droits();
		droit.setId(idDroit);
		droit.setIdTypeDroit(TypeDroitEnum.CONSULTATION.getIdTypeDroit());
		droit.setElement(element);
		droit.setDroitsGroupe(groupeUtilisateur);
		sirhPersistenceUnit.persist(droit);
		
		Set<Droits> droits = new HashSet<Droits>();
		droits.add(droit);
		
		groupeUtilisateur.setDroits(droits);
		
		List<Droits> result = repository.getDroitsByElementAndAgent(10, "rebjo84");
		
		assertTrue(result.isEmpty());
	}
}
