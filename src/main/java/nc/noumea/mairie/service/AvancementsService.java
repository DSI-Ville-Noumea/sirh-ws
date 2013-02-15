package nc.noumea.mairie.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Cap;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
public class AvancementsService implements IAvancementsService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	private EntityManager sirhEntityManager;
	
	@Override
	public CommissionAvancementDto getCommissionsForCapAndCadreEmploi(int idCap, int idCadreEmploi) {

		throw new NotImplementedException();
	}
	
	@Override
	public List<Agent> getAgentsForCommission(int idCap, int idCadreEmploi) {
		
		List<Agent> result = null;
		
		TypedQuery<Cap> q = sirhEntityManager.createQuery("select c from Cap c", Cap.class);
		List<Cap> r = q.getResultList();
		
		return result;
	}

}
