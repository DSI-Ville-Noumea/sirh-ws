package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import nc.noumea.mairie.model.bean.sirh.BaseHorairePointage;
import nc.noumea.mairie.web.dto.BaseHorairePointageDto;

import org.springframework.stereotype.Service;

@Service
public class PointageService implements IPointageService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Override
	public List<Integer> getPrimePointagesByAgent(Integer idAgent, Date dateDebut, Date dateFin) {
		StringBuilder sb = new StringBuilder();
		sb.append("select paff.num_rubrique from affectation aff ");
		sb.append("inner join prime_pointage_aff paff on aff.id_affectation=paff.id_affectation ");
		sb.append("where aff.id_agent = :idAgent and aff.date_debut_Aff <= :dateFin ");
		sb.append("and (aff.date_fin_Aff is null or aff.date_fin_Aff >= :dateDebut) ");
		sb.append("order by paff.num_rubrique ");

		Query q = sirhEntityManager.createNativeQuery(sb.toString());
		q.setParameter("idAgent", idAgent);
		q.setParameter("dateDebut", dateDebut);
		q.setParameter("dateFin", dateFin);

		@SuppressWarnings("unchecked")
		List<Integer> result = q.getResultList();

		return result;
	}

	@Override
	public List<BaseHorairePointageDto> getBaseHorairePointageByAgent(Integer idAgent, Date dateDebut, Date dateFin) {
		StringBuilder sb = new StringBuilder();
		sb.append("select param.* from affectation aff ");
		sb.append("inner join p_base_horaire_pointage param on aff.id_base_horaire_pointage=param.id_base_horaire_pointage ");
		sb.append("where aff.id_agent = :idAgent and aff.date_debut_aff <= :dateFin ");
		sb.append("and (aff.date_fin_aff is null or aff.date_fin_aff >= :dateDebut) order by aff.date_debut_aff ");

		Query q = sirhEntityManager.createNativeQuery(sb.toString(), BaseHorairePointage.class);
		q.setParameter("idAgent", idAgent);
		q.setParameter("dateDebut", dateDebut);
		q.setParameter("dateFin", dateFin);

		@SuppressWarnings("unchecked")
		List<BaseHorairePointage> result = q.getResultList();

		List<BaseHorairePointageDto> resultDto = new ArrayList<BaseHorairePointageDto>();
		if(null != result
				&& !result.isEmpty()) {

			for(BaseHorairePointage baseHorairePointage : result) {
				resultDto.add(new BaseHorairePointageDto(baseHorairePointage));
			}
		}
		
		return resultDto;
	}
}
