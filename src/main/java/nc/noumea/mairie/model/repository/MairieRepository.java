package nc.noumea.mairie.model.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.Spbhor;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spmtsr;

import org.springframework.stereotype.Repository;

@Repository
public class MairieRepository implements IMairieRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getListeCarriereActiveAvecPAAffecte() {

		StringBuilder sb = new StringBuilder();

		sb.append(" select cast(carr.nomatr as int) nomatr from Spcarr carr ");
		sb.append(" inner join SPADMN pa on carr.nomatr = pa.nomatr ");
		sb.append(" where (pa.datfin = 0 or pa.datfin >= :dateJourMairie ) ");
		sb.append(" and pa.CDPADM in('58','54','56','57','67') ");
		sb.append(" and carr.cdcate  in (2,1)  and carr.datdeb = ");
		sb.append(" (select max(c.datdeb) from spcarr c where c.nomatr = carr.nomatr and c.datdeb <= :dateJourMairie ");
		sb.append(" and (c.datfin=0 or c.datfin >= :dateJourMairie )) ");
		sb.append(" and pa.datdeb <= :dateJourMairie ");
		sb.append(" and (pa.datfin=0 or pa.datfin >= :dateJourMairie ) ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString());

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		query.setParameter("dateJourMairie", Integer.valueOf(sdfMairie.format(new Date())));

		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getListeCarriereActiveAvecPA() {

		StringBuilder sb = new StringBuilder();

		sb.append("select cast(carr.nomatr as int) nomatr from Spcarr carr ");
		sb.append(" inner join SPADMN pa on carr.nomatr = pa.nomatr ");
		sb.append(" where (pa.datfin = 0 or pa.datfin >= :dateJourMairie ) ");
		sb.append(" and LENGTH(TRIM(TRANSLATE(pa.cdpadm ,' ', ' +-.0123456789'))) = 0 ");
		sb.append(" and carr.CDCATE not in (7,9,10,11) ");
		sb.append(" and pa.cdpadm not in('CA','DC','DE','FC','LI','RF','RT','RV') ");
		sb.append(" and carr.datdeb = ( ");
		sb.append(" select max(c.datdeb) from Spcarr c where c.nomatr = carr.nomatr ");
		sb.append(" and c.datdeb <= :dateJourMairie ");
		sb.append(" and (c.DATFIN = 0 or c.DATFIN >= :dateJourMairie )) ");
		sb.append(" and pa.datdeb <= :dateJourMairie ");
		sb.append(" and (pa.datfin=0 or pa.datfin >= :dateJourMairie ) ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString());

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		query.setParameter("dateJourMairie", Integer.valueOf(sdfMairie.format(new Date())));

		return query.getResultList();
	}

	@Override
	public Spadmn chercherPositionAdmAgentAncienne(Integer noMatr) {

		TypedQuery<Spadmn> q = sirhEntityManager
				.createQuery(
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

		TypedQuery<Spadmn> q = sirhEntityManager
				.createQuery(
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
	public List<Spmtsr> getListSpmtsr(Integer noMatricule) {

		TypedQuery<Spmtsr> q = sirhEntityManager.createQuery(
				"select a from Spmtsr a where a.id.nomatr = :noMatricule order by a.id.datdeb ", Spmtsr.class);

		q.setParameter("noMatricule", noMatricule);

		return q.getResultList();
	}

	@Override
	public Spcarr getCarriereFonctionnaireAncienne(Integer noMatr) {

		TypedQuery<Spcarr> qCarr = sirhEntityManager.createNamedQuery("getCarriereFonctionnaireAncienne", Spcarr.class);
		qCarr.setParameter("nomatr", noMatr);

		List<Spcarr> result = qCarr.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}

	@Override
	public Spcarr getCarriereActive(Integer noMatr) {

		TypedQuery<Spcarr> qCarr = sirhEntityManager.createNamedQuery("getCurrentCarriere", Spcarr.class);
		qCarr.setParameter("nomatr", noMatr);
		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		qCarr.setParameter("todayFormatMairie", Integer.valueOf(sdfMairie.format(new Date())));

		List<Spcarr> result = qCarr.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}
	
	@Override
	public List<Spbhor> getListSpbhor() {
		
		return sirhEntityManager.createNamedQuery("Spbhor.whereCdTauxNotZero", Spbhor.class)
				.getResultList();
	}
	
	@Override
	public Spbhor getSpbhorById(Integer idSpbhor) {
		
		return sirhEntityManager.find(Spbhor.class, idSpbhor);
	}
}
