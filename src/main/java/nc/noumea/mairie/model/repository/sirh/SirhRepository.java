package nc.noumea.mairie.model.repository.sirh;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.joda.time.DateTime;
import org.springframework.stereotype.Repository;

import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.DestinataireMailMaladie;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;
import nc.noumea.mairie.model.bean.sirh.JourFerie;
import nc.noumea.mairie.model.bean.sirh.Utilisateur;

@Repository
public class SirhRepository implements ISirhRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public AutreAdministrationAgent chercherAutreAdministrationAgentAncienne(Integer idAgent, boolean isFonctionnaire) {

		StringBuilder sb = new StringBuilder();
		sb.append("select a from AutreAdministrationAgent a where a.autreAdministrationAgentPK.idAgent = :idAgent ");
		if (isFonctionnaire) {
			sb.append(" and a.fonctionnaire = 1 ");
		}
		sb.append(" and a.autreAdministrationAgentPK.dateEntree = ( ");
		sb.append(" select min(aa.autreAdministrationAgentPK.dateEntree) from AutreAdministrationAgent aa where a.autreAdministrationAgentPK.idAgent = aa.autreAdministrationAgentPK.idAgent) ");

		TypedQuery<AutreAdministrationAgent> q = sirhEntityManager.createQuery(sb.toString(),
				AutreAdministrationAgent.class);

		q.setParameter("idAgent", idAgent);

		List<AutreAdministrationAgent> result = q.getResultList();

		if (null != result && !result.isEmpty())
			return q.getSingleResult();

		return null;
	}

	@Override
	public List<AutreAdministrationAgent> getListeAutreAdministrationAgent(Integer idAgent) {

		StringBuilder sb = new StringBuilder();
		sb.append("select a from AutreAdministrationAgent a where a.autreAdministrationAgentPK.idAgent = :idAgent order by a.autreAdministrationAgentPK.dateEntree ");

		TypedQuery<AutreAdministrationAgent> q = sirhEntityManager.createQuery(sb.toString(),
				AutreAdministrationAgent.class);
		q.setParameter("idAgent", idAgent);

		return q.getResultList();
	}

	@Override
	public AvancementFonctionnaire getDernierAvancement(Integer idAgent) {
		Integer annee = new DateTime().getYear();
		
		StringBuilder sb = new StringBuilder();
		sb.append("select a from AvancementFonctionnaire a where a.agent.idAgent = :idAgent and a.anneeAvancement <= :annee order by a.anneeAvancement desc");

		TypedQuery<AvancementFonctionnaire> q = sirhEntityManager.createQuery(sb.toString(), AvancementFonctionnaire.class);
		q.setParameter("idAgent", idAgent);
		q.setParameter("annee", annee);

		return q.getResultList().size() != 0 ? q.getResultList().get(0) : null;
	}

	@Override
	public List<DiplomeAgent> getListDiplomeByAgent(Integer idAgent) {

		TypedQuery<DiplomeAgent> q = sirhEntityManager
				.createQuery("select a from DiplomeAgent a where a.idAgent = :idAgent order by a.dateObtention ",
						DiplomeAgent.class);

		q.setParameter("idAgent", idAgent);

		return q.getResultList();
	}

	@Override
	public List<FormationAgent> getListFormationAgentByAnnee(Integer idAgent, Integer anneeFormation) {

		TypedQuery<FormationAgent> q = sirhEntityManager
				.createQuery(
						"select a from FormationAgent a where a.idAgent = :idAgent and a.anneeFormation = :anneeFormation order by a.idFormation desc ",
						FormationAgent.class);

		q.setParameter("idAgent", idAgent);
		q.setParameter("anneeFormation", anneeFormation);

		return q.getResultList();
	}
	
	@Override
	public List<JourFerie> getListeJoursFeries(Date dateDebut, Date dateFin) {
		
		TypedQuery<JourFerie> q = sirhEntityManager.createNamedQuery("listJoursFeriesChomesByPeriode", JourFerie.class);
		q.setParameter("dateDebut", dateDebut);
		q.setParameter("dateFin", dateFin);

		return q.getResultList();
	}
	
	@Override
	public List<Utilisateur> getListeUtilisateur() {

		TypedQuery<Utilisateur> q = sirhEntityManager.createNamedQuery("getAllUtilisateurs", Utilisateur.class);
		
		return q.getResultList();
	}


	@Override
	public List<DestinataireMailMaladie> getListDestinataireMailMaladie(boolean isForJob) {
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct(dest) from DestinataireMailMaladie dest ");
		sb.append("join fetch dest.groupe gr ");
		sb.append("join fetch gr.utilisateurs ");
		sb.append("join fetch gr.utilisateurs ");
		sb.append("where dest.isForJob = :isForJob ");
		sb.append("order by dest.idDestinataireMailMaladie ");

		TypedQuery<DestinataireMailMaladie> q = sirhEntityManager.createQuery(sb.toString(), DestinataireMailMaladie.class);
		
		q.setParameter("isForJob", isForJob);

		return q.getResultList();
	}
}
