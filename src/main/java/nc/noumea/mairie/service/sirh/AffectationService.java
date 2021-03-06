package nc.noumea.mairie.service.sirh;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.web.dto.AffectationDto;
import nc.noumea.mairie.web.dto.RefTypeAbsenceDto;
import nc.noumea.mairie.ws.ISirhAbsWSConsumer;

@Service
public class AffectationService implements IAffectationService {
	
	private Logger logger = LoggerFactory.getLogger(AffectationService.class);

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private IAffectationRepository affRepo;

	@Autowired
	private ISirhAbsWSConsumer		sirhAbsWSConsumer;

	@Override
	public Affectation getAffectationById(Integer idAffectation) {

		Affectation res = null;
		TypedQuery<Affectation> query = sirhEntityManager.createQuery("select aff from Affectation aff where aff.idAffectation=:idAffectation", Affectation.class);
		query.setParameter("idAffectation", idAffectation);

		List<Affectation> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

	@Override
	public List<Affectation> getListAffectationByIdFichePoste(Integer idFichePoste) {
		return affRepo.getAffectationByIdFichePoste(idFichePoste);
	}

	@Override
	public Affectation getAffectationByIdFichePoste(Integer idFichePoste) {

		Affectation res = null;
		TypedQuery<Affectation> query = sirhEntityManager.createQuery(
				"select aff from Affectation aff where aff.fichePoste.idFichePoste=:idFichePoste and aff.dateDebutAff <= :dateJour and (aff.dateFinAff is null or aff.dateFinAff >= :dateJour)",
				Affectation.class);
		query.setParameter("idFichePoste", idFichePoste);
		query.setParameter("dateJour", new Date());

		List<Affectation> lfp = query.getResultList();
		if (lfp != null && lfp.size() > 0) {
			res = lfp.get(0);
		}

		return res;
	}

	@Override
	public List<Affectation> getListAffectationActiveByIdFichePoste(Integer idFichePoste) {
		return affRepo.getListAffectationActiveByIdFichePoste(idFichePoste);
	}
	
	@Override
	public List<AffectationDto> getListeAffectationDtosForListAgentByPeriode(List<Integer> listIdsAgent, Date dateDebut, Date dateFin) {
		
		List<Affectation> listAffectation = affRepo.getListeAffectationsForListAgentByPeriode(listIdsAgent, dateDebut, dateFin);
		
		List<AffectationDto> result = new ArrayList<AffectationDto>();
		
		if(null != listAffectation) {
			for(Affectation aff : listAffectation) {
				if (aff.getIdBaseHoraireAbsence() == null) {
					logger.warn("Il n'y a pas de base horaire absence correspondant à l'affectation id {}.", aff.getIdAffectation());
					continue;
				}
				RefTypeAbsenceDto dtoBaseHoraireAbs = sirhAbsWSConsumer.getTypAbsenceCongeAnnuelById(aff.getIdBaseHoraireAbsence());
				AffectationDto dto = new AffectationDto(aff, dtoBaseHoraireAbs.getTypeSaisiCongeAnnuelDto());
				result.add(dto);
			}
		}
		
		return result;
	}
}
