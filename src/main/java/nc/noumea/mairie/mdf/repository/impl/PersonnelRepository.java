package nc.noumea.mairie.mdf.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import nc.noumea.mairie.mdf.domain.cde.adm.FdsMutDetAdm;
import nc.noumea.mairie.mdf.domain.cde.adm.FdsMutEntAdm;
import nc.noumea.mairie.mdf.domain.cde.adm.FdsMutTotAdm;
import nc.noumea.mairie.mdf.domain.cde.pers.FdsMutDetPers;
import nc.noumea.mairie.mdf.domain.cde.pers.FdsMutEntPers;
import nc.noumea.mairie.mdf.domain.cde.pers.FdsMutTotPers;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutDet;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutEnt;
import nc.noumea.mairie.mdf.domain.vdn.FdsMutTot;
import nc.noumea.mairie.mdf.repository.IPersonnelRepository;

@Repository
public class PersonnelRepository implements IPersonnelRepository {

	@PersistenceContext(unitName = "mdfVdnPersistenceUnit")
	private EntityManager vdnEntityManager;

	@PersistenceContext(unitName = "mdfCdePersistenceUnit")
	private EntityManager cdeEntityManager;

	private Logger logger = LoggerFactory.getLogger(PersonnelRepository.class);

	@Override
	public FdsMutEnt getEnteteVdn() {
		TypedQuery<FdsMutEnt> q = vdnEntityManager.createQuery("select a from FdsMutEnt a", FdsMutEnt.class);

		FdsMutEnt res = null;
		try {
			res = q.getSingleResult();
		} catch (NonUniqueResultException e) {
			logger.error("Plusieurs enregistrements d'en-tête (FDSMUTENT) on été retournés. Le bordereau récapitulatif n'a donc pas pu être généré.");
		}

		return res;
	}

	@Override
	public FdsMutTot getTotalVdn() {
		TypedQuery<FdsMutTot> q = vdnEntityManager.createQuery("select a from FdsMutTot a", FdsMutTot.class);

		FdsMutTot res = null;
		try {
			res = q.getSingleResult();
		} catch (NonUniqueResultException e) {
			logger.error("Plusieurs enregistrements de total (FDSMUTTOT) on été retournés. Le bordereau récapitulatif n'a donc pas pu être généré.");
		}

		return res;
	}

	@Override
	public List<FdsMutDet> getAllDetailsVdn() {
		TypedQuery<FdsMutDet> q = vdnEntityManager.createQuery("select a from FdsMutDet a", FdsMutDet.class);
		return q.getResultList();
	}

	@Override
	public FdsMutEntAdm getEnteteAdm() {
		TypedQuery<FdsMutEntAdm> q = cdeEntityManager.createQuery("select a from FdsMutEntAdm a", FdsMutEntAdm.class);

		FdsMutEntAdm res = null;
		try {
			res = q.getSingleResult();
		} catch (NonUniqueResultException e) {
			logger.error("Plusieurs enregistrements d'en-tête (FdsMutEntAdm) on été retournés. Le bordereau récapitulatif n'a donc pas pu être généré.");
		}

		return res;
	}

	@Override
	public FdsMutTotAdm getTotalAdm() {
		TypedQuery<FdsMutTotAdm> q = cdeEntityManager.createQuery("select a from FdsMutTotAdm a", FdsMutTotAdm.class);

		FdsMutTotAdm res = null;
		try {
			res = q.getSingleResult();
		} catch (NonUniqueResultException e) {
			logger.error("Plusieurs enregistrements de total (FdsMutTotAdm) on été retournés. Le bordereau récapitulatif n'a donc pas pu être généré.");
		}

		return res;
	}

	@Override
	public List<FdsMutDetAdm> getAllDetailsAdm() {
		TypedQuery<FdsMutDetAdm> q = cdeEntityManager.createQuery("select a from FdsMutDetAdm a", FdsMutDetAdm.class);
		return q.getResultList();
	}

	@Override
	public FdsMutEntPers getEntetePers() {
		TypedQuery<FdsMutEntPers> q = cdeEntityManager.createQuery("select a from FdsMutEntPers a", FdsMutEntPers.class);

		FdsMutEntPers res = null;
		try {
			res = q.getSingleResult();
		} catch (NonUniqueResultException e) {
			logger.error("Plusieurs enregistrements d'en-tête (FdsMutEntPers) on été retournés. Le bordereau récapitulatif n'a donc pas pu être généré.");
		}

		return res;
	}

	@Override
	public FdsMutTotPers getTotalPers() {
		TypedQuery<FdsMutTotPers> q = cdeEntityManager.createQuery("select a from FdsMutTotPers a", FdsMutTotPers.class);

		FdsMutTotPers res = null;
		try {
			res = q.getSingleResult();
		} catch (NonUniqueResultException e) {
			logger.error("Plusieurs enregistrements de total (FdsMutTotPers) on été retournés. Le bordereau récapitulatif n'a donc pas pu être généré.");
		}

		return res;
	}

	@Override
	public List<FdsMutDetPers> getAllDetailsPers() {
		TypedQuery<FdsMutDetPers> q = cdeEntityManager.createQuery("select a from FdsMutDetPers a", FdsMutDetPers.class);
		return q.getResultList();
	}
}
