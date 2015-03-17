package nc.noumea.mairie.model.repository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spprim;

import org.springframework.stereotype.Repository;

@Repository
public class SpprimRepository implements ISpprimRepository {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;

	@Override
	public List<Integer> getListChefsService() {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from Spprim a where a.id.norubr = :norubr ");
		sb.append("and a.montantPrime = :montantPrime ");
		sb.append("and ((:dateJourMairie between a.id.datdeb and a.datfin) or (a.id.datdeb <= :dateJourMairie and a.datfin = 0 )) ");

		TypedQuery<Spprim> q = sirhEntityManager.createQuery(sb.toString(), Spprim.class);
		q.setParameter("norubr", 7079);
		q.setParameter("montantPrime", 48.0);

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		q.setParameter("dateJourMairie", Integer.valueOf(sdfMairie.format(new Date())));

		List<Spprim> resultChef = q.getResultList();
		List<Integer> result = new ArrayList<Integer>();
		for (Spprim prime : resultChef) {
			result.add(new Integer("900" + prime.getId().getNomatr()));
		}
		return result;
	}

	@Override
	public List<Integer> getListDirecteur() {
		StringBuilder sb = new StringBuilder();
		sb.append("select a from Spprim a where a.id.norubr = :norubr ");
		sb.append("and a.montantPrime = :montantPrime ");
		sb.append("and ((:dateJourMairie between a.id.datdeb and a.datfin) or (a.id.datdeb <= :dateJourMairie and a.datfin = 0 )) ");

		TypedQuery<Spprim> q = sirhEntityManager.createQuery(sb.toString(), Spprim.class);
		q.setParameter("norubr", 7079);
		q.setParameter("montantPrime", 88.0);

		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
		q.setParameter("dateJourMairie", Integer.valueOf(sdfMairie.format(new Date())));

		List<Spprim> resultChef = q.getResultList();
		List<Integer> result = new ArrayList<Integer>();
		for (Spprim prime : resultChef) {
			result.add(new Integer("900" + prime.getId().getNomatr()));
		}
		return result;
	}
}
