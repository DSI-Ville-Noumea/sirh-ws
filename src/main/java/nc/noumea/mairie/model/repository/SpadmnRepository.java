package nc.noumea.mairie.model.repository;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import nc.noumea.mairie.model.bean.PositDesc;
import nc.noumea.mairie.model.bean.Spadmn;

@Repository
public class SpadmnRepository implements ISpadmnRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public Spadmn chercherPositionAdmAgentAncienne(Integer noMatr) {

		TypedQuery<Spadmn> q = sirhEntityManager.createQuery(
				"select a from Spadmn a where a.id.nomatr = :noMatr and a.id.datdeb = (select min(pa.id.datdeb) from Spadmn pa where pa.id.nomatr = a.id.nomatr )",
				Spadmn.class);
		q.setParameter("noMatr", noMatr);

		List<Spadmn> result = q.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}

	@Override
	public Spadmn chercherPositionAdmAgentEnCours(Integer noMatr) {

		TypedQuery<Spadmn> q = sirhEntityManager.createQuery(
				"select a from Spadmn a where a.id.nomatr = :noMatr and ( (:dateJourMairie between a.id.datdeb and a.datfin) or (a.id.datdeb <= :dateJourMairie and a.datfin = 0 ))",
				Spadmn.class);
		q.setParameter("noMatr", noMatr);

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		q.setParameter("dateJourMairie", Integer.valueOf(sdfMairie.format(new Date())));

		List<Spadmn> result = q.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}

	@Override
	public List<Spadmn> chercherListPositionAdmAgentSurPeriodeDonnee(Integer noMatr, Date dateDebut, Date dateFin) {

		TypedQuery<Spadmn> q = sirhEntityManager.createQuery("select a from Spadmn a where a.id.nomatr = :noMatr and "
				+ " ( (a.id.datdeb <= :datfin) and (a.datfin = 0 or a.datfin >= :datdeb )) ", Spadmn.class);
		q.setParameter("noMatr", noMatr);

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		q.setParameter("datdeb", Integer.valueOf(sdfMairie.format(dateDebut)));
		q.setParameter("datfin", Integer.valueOf(sdfMairie.format(dateFin)));

		return q.getResultList();
	}

	@Override
	public List<Spadmn> chercherListPositionAdmAgentAncienne(Integer noMatr, Integer dateLimite) {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from Spadmn a ");
		sb.append("where a.id.nomatr = :noMatr ");
		if (dateLimite != null)
			sb.append("and a.id.datdeb < :dateLimite ");
		sb.append("order by a.id.datdeb desc ");

		TypedQuery<Spadmn> q = sirhEntityManager.createQuery(sb.toString(), Spadmn.class);
		q.setParameter("noMatr", noMatr);
		if (dateLimite != null)
			q.setParameter("dateLimite", dateLimite);

		return q.getResultList();
	}

	@Override
	public List<Integer> listAgentActiviteAnnuaire() {
		StringBuilder sb = new StringBuilder();
		sb.append("select a.id.nomatr as nomatr from Spadmn a ");
		sb.append("where  a.positionAdministrative.cdpAdm in (select an.cdpAdm from SpposaAnnuaire an) ");
		sb.append("AND  a.id.datdeb <= :dateFormatMairie and (a.datfin > :dateFormatMairie or a.datfin = 0 or a.datfin is null) ");
		sb.append("AND  a.id.nomatr < 9000 ");
		sb.append("ORDER BY nomatr ");

		TypedQuery<Integer> q = sirhEntityManager.createQuery(sb.toString(), Integer.class);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		int dateFormatMairie = Integer.valueOf(sdf.format(new Date()));
		q.setParameter("dateFormatMairie", dateFormatMairie);

		return q.getResultList();
	}

	@Override
	public PositDesc chercherPositDescByPosit(String position) {

		TypedQuery<PositDesc> q = sirhEntityManager.createQuery("select a from PositDesc a where a.position = :position ", PositDesc.class);
		q.setParameter("position", position);

		List<PositDesc> result = q.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}

	@Override
	public List<Integer> listNomatrEnActiviteSurPeriode(Date dateDebutPeriode, Date dateFinPeriode) {
		// on recupere tous les nomatr actifs dans SPADMN
		StringBuilder sbSpadmn = new StringBuilder();
		sbSpadmn.append("select distinct(a.id.nomatr) from Spadmn a ");
		sbSpadmn.append("where ( (a.id.datdeb <= :datfin) and (a.datfin = 0 or a.datfin >= :datdeb )) ");
		sbSpadmn.append("and a.positionAdministrative.cdpAdm in (:listPAActiveForTitreRepas) ");
		sbSpadmn.append("and a.id.nomatr < 9000 ");
		sbSpadmn.append("ORDER BY a.id.nomatr ");

		TypedQuery<Integer> q = sirhEntityManager.createQuery(sbSpadmn.toString(), Integer.class);

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		q.setParameter("datdeb", Integer.valueOf(sdfMairie.format(dateDebutPeriode)));
		q.setParameter("datfin", Integer.valueOf(sdfMairie.format(dateFinPeriode)));
		q.setParameter("listPAActiveForTitreRepas", Arrays.asList("01", "02", "03", "04", "23", "24", "60", "61", "62", "63", "64", "65", "66"));

		return q.getResultList();

	}

	@Override
	public List<Integer> listNomatrEnActivitePourHistoContrats() {
		// on recupere tous les nomatr actifs dans SPADMN
		StringBuilder sbSpadmn = new StringBuilder();
		sbSpadmn.append("select distinct(a.id.nomatr) from Spadmn a ");
		sbSpadmn.append("where ( (a.id.datdeb <= :currDate) and (a.datfin = 0 or a.datfin >= :currDate )) ");
		sbSpadmn.append("and a.positionAdministrative.cdpAdm not in (:listPAInactiveForTiarhe) ");
		sbSpadmn.append("and a.id.nomatr < 9000 ");
		sbSpadmn.append("ORDER BY a.id.nomatr ");

		TypedQuery<Integer> q = sirhEntityManager.createQuery(sbSpadmn.toString(), Integer.class);

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		q.setParameter("currDate", Integer.valueOf(sdfMairie.format(new Date())));
		q.setParameter("listPAInactiveForTiarhe", Arrays.asList("CA", "DC", "DE", "FC", "FI", "LI", "RT", "RV"));

		return q.getResultList();
	}
}
