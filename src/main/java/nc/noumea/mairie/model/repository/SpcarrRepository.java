package nc.noumea.mairie.model.repository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.SpcarrWithoutSpgradn;

import org.springframework.stereotype.Repository;

@Repository
public class SpcarrRepository implements ISpcarrRepository {

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Spcarr> listerCarriereAvecGradeEtStatut(Integer nomatr, String cdgrad, Integer codeCategorie) {
		StringBuilder sb = new StringBuilder();

		sb.append(" select carr.* from Spcarr carr ");
		sb.append(" where carr.CDGRAD = :cdGrad ");
		sb.append(" and carr.NOMATR = :nomatr ");
		sb.append(" and carr.CDCATE = :cdCate ");

		Query query = sirhEntityManager.createNativeQuery(sb.toString(), Spcarr.class);

		query.setParameter("cdGrad", cdgrad);
		query.setParameter("nomatr", nomatr);
		query.setParameter("cdCate", codeCategorie);

		return query.getResultList();
	}

	@Override
	public SpcarrWithoutSpgradn getCarriereActiveWithoutGrad(Integer noMatr) {

		TypedQuery<SpcarrWithoutSpgradn> qCarr = sirhEntityManager.createNamedQuery("getCurrentCarriereWithoutSpgradn",
				SpcarrWithoutSpgradn.class);
		qCarr.setParameter("nomatr", noMatr);
		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		qCarr.setParameter("todayFormatMairie", Integer.valueOf(sdfMairie.format(new Date())));

		List<SpcarrWithoutSpgradn> result = qCarr.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}

	@Override
	public SpcarrWithoutSpgradn getCarriereFonctionnaireAncienneGrad(Integer noMatr) {

		TypedQuery<SpcarrWithoutSpgradn> qCarr = sirhEntityManager.createNamedQuery(
				"getCarriereFonctionnaireAncienneWithoutSpgradn", SpcarrWithoutSpgradn.class);
		qCarr.setParameter("nomatr", noMatr);

		List<SpcarrWithoutSpgradn> result = qCarr.getResultList();
		if (null != result && !result.isEmpty())
			return result.get(0);

		return null;
	}
}
