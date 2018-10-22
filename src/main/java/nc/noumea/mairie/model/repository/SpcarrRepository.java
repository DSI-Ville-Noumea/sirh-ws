package nc.noumea.mairie.model.repository;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.EnumSet;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;

import nc.noumea.mairie.mdf.domain.InactivePAEnum;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.SpcarrWithoutSpgradn;
import nc.noumea.mairie.web.dto.CarriereDto;

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
		sb.append(" order by carr.DATDEB ");

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

	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getListeAgentsPourAlimAutoCongesAnnuels(Date datdeb, Date datfin) {

		StringBuilder sb = new StringBuilder();

		sb.append("select cast(carr.nomatr as int) nomatr from Spcarr carr ");
		sb.append(" inner join SPADMN pa on carr.nomatr = pa.nomatr ");
		sb.append(" inner join SPPOSA posa on pa.CDPADM = posa.CDPADM and posa.DROITC <> 'N' ");
		sb.append(" WHERE carr.CDCATE not in (9,10,11) ");
		sb.append(" and ( (pa.datdeb <= :datdeb ");
		sb.append(" and (pa.datfin=0 or pa.datfin > :datdeb )) ");
		sb.append(" or (pa.datdeb <= :datfin ");
		sb.append(" and (pa.datfin=0 or pa.datfin > :datfin ) )) ");	
		sb.append(" and ( (carr.datdeb <= :datdeb ");
		sb.append(" and (carr.datfin=0 or carr.datfin >= :datdeb )) ");
		sb.append(" or (carr.datdeb <= :datfin ");
		sb.append(" and (carr.datfin=0 or carr.datfin >= :datfin ) )) ");
		sb.append(" and carr.nomatr < 9000 ");
		sb.append(" GROUP BY carr.nomatr ");

		// on exclut les categories 9, 10, 11
		// qui correspondent aux adjoints + les conseillers municipaux + le
		// maire
		// donc pas de routine de conges annuels pour eux

		Query query = sirhEntityManager.createNativeQuery(sb.toString());

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		query.setParameter("datdeb", Integer.valueOf(sdfMairie.format(datdeb)));
		query.setParameter("datfin", Integer.valueOf(sdfMairie.format(datfin)));

		return query.getResultList();
	}

	@Override
	public Integer getListeAgentsActifsPourGenerationBordereauMDF(Date dateDebut, Date dateFin) {

		StringBuilder sb = new StringBuilder();
		
		List<String> listPAInactives = new ArrayList<String>();
		EnumSet<InactivePAEnum> types = EnumSet.allOf(InactivePAEnum.class);
		for (InactivePAEnum PA : types) {
			listPAInactives.add(PA.getCode());
		}
		
		// 2e requête simplifiée (différence au niveau des dates)
		sb.append("select count(distinct(carr.nomatr)) from Spcarr carr ");
		sb.append(" inner join SPADMN pa on carr.nomatr = pa.nomatr ");
		sb.append(" inner join SPPOSA posa on pa.CDPADM = posa.CDPADM ");
		sb.append(" WHERE (pa.datdeb <= :datfin ");
		sb.append(" and (pa.datfin=0 or pa.datfin >= :datdeb )) ");	
		sb.append(" and (carr.datdeb <= :datfin ");
		sb.append(" and (carr.datfin=0 or carr.datfin >= :datdeb )) ");
		sb.append(" and carr.nomatr < 9000 ");
		sb.append(" and pa.CDPADM not in (:listPAInactives)");
		// #42537 : Les adjoints, conseillers municipaux et maire ne font pas partie des effectifs mairie
		sb.append(" and carr.CDCATE not in ('9', '10', '11')");

		Query query = null;
		query = sirhEntityManager.createNativeQuery(sb.toString());

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		query.setParameter("datdeb", Integer.valueOf(sdfMairie.format(dateDebut)));
		query.setParameter("datfin", Integer.valueOf(sdfMairie.format(dateFin)));
		query.setParameter("listPAInactives", listPAInactives);

		return (Integer) query.getSingleResult();
	}

	@Override
	public List<CarriereDto> getAllCarrieresByAgentForTiarhe(Integer idAgent) throws ParseException {
		StringBuilder sb = new StringBuilder();

		sb.append("select max(cat.licate), max(bar.iban), max(bar.inm), min(carr.datdeb), max(carr.datfin), carr.cddcdica ");
		sb.append("from Spcarr carr, SPBAREM bar, SPCATG cat ");
		sb.append("where carr.nomatr = :nomatr AND carr.iban = bar.iban AND cat.cdcate = carr.cdcate ");
		sb.append("group by carr.cdcate, carr.iban, cddcdica ");
		sb.append("order by min(carr.datdeb)");

		Query query = sirhEntityManager.createNativeQuery(sb.toString());

		query.setParameter("nomatr", idAgent - 9000000);
		
		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		List<CarriereDto> returnList = Lists.newArrayList();
		CarriereDto newCarr;
		
		for (Object[] o : (List<Object[]>)query.getResultList()) {
			newCarr = new CarriereDto();

			newCarr.setLibelleCategorie((String)o[0]);
			
			newCarr.setIBAN((String)o[1]);
			newCarr.setINM(((BigDecimal)o[2]).toString());
			newCarr.setDateDebut(sdfMairie.parse(((BigDecimal)o[3]).toString()));
			if (!((BigDecimal)o[4]).toString().equals("0"))
				newCarr.setDateFin(sdfMairie.parse(((BigDecimal)o[4]).toString()));
			newCarr.setTypeContrat((String)o[5]);
			
			returnList.add(newCarr);
		}
		
		return returnList;
	}
}
