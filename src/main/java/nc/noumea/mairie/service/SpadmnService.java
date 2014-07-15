package nc.noumea.mairie.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.repository.ISpadmnRepository;
import nc.noumea.mairie.web.dto.PositionAdmAgentDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpadmnService implements ISpadmnService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private ISpadmnRepository spadmnRepository;

	@Override
	public boolean estPAActive(Integer nomatr, Integer dateDeb) {
		String sql = "select count(pa.nomatr) from spadmn pa inner join spposa po on pa.cdpadm=po.cdpadm where pa.nomatr=:nomatr and pa.datdeb <=:dateJour and ( pa.datfin =0 or pa.datfin>=:dateJour) and po.posit!='FS'";
		Query query = sirhEntityManager.createNativeQuery(sql);
		query.setParameter("nomatr", nomatr);
		query.setParameter("dateJour", dateDeb);
		Integer nbRes = (Integer) query.getSingleResult();
		return nbRes > 0;
	}

	@Override
	public PositionAdmAgentDto chercherPositionAdmAgentAncienne(Integer noMatr) {

		PositionAdmAgentDto dto = null;

		Spadmn spAdm = spadmnRepository.chercherPositionAdmAgentAncienne(noMatr);

		if (null != spAdm) {
			dto = new PositionAdmAgentDto(spAdm);
		}

		return dto;
	}

	@Override
	public PositionAdmAgentDto chercherPositionAdmAgentEnCours(Integer noMatr) {

		PositionAdmAgentDto dto = null;

		Spadmn spAdm = spadmnRepository.chercherPositionAdmAgentEnCours(noMatr);

		if (null != spAdm) {
			dto = new PositionAdmAgentDto(spAdm);
		}

		return dto;
	}
}
