package nc.noumea.mairie.service.sirh;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import nc.noumea.mairie.model.bean.Spadmn;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.repository.ISpadmnRepository;
import nc.noumea.mairie.model.repository.ISpcarrRepository;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.web.dto.InfosAlimAutoCongesAnnuelsDto;
import nc.noumea.mairie.web.dto.RefTypeSaisiCongeAnnuelDto;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AbsenceService implements IAbsenceService {

	@PersistenceContext(unitName = "sirhPersistenceUnit")
	transient EntityManager sirhEntityManager;

	@Autowired
	private ISpadmnRepository spadmnRepository;
	
	@Autowired
	private ISpcarrRepository spcarrRepository;
	
	@Autowired
	private IAffectationRepository affectationRepository;
	
	@Autowired
	private HelperService helper;
	
	private SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");
	
	private Logger logger = LoggerFactory.getLogger(AbsenceService.class);
	
	@Override
	public RefTypeSaisiCongeAnnuelDto getBaseHoraireAbsenceByAgent(Integer idAgent, Date date) {

		TypedQuery<Affectation> q = sirhEntityManager
				.createNamedQuery("getAffectationActiveByAgent", Affectation.class);
		q.setParameter("idAgent", idAgent);
		q.setParameter("today", date);
		q.setMaxResults(1);

		RefTypeSaisiCongeAnnuelDto result = null;
		try {

			List<Affectation> aff = q.getResultList();
			result = new RefTypeSaisiCongeAnnuelDto();
			result.setIdRefTypeSaisiCongeAnnuel(aff.get(0).getIdBaseHoraireAbsence());
		} catch (Exception e) {
			// on ne fait rien
		}

		return result;
	}
	
	@Override
	public List<InfosAlimAutoCongesAnnuelsDto> getListPAPourAlimAutoCongesAnnuels(Integer idAgent, Date dateDebut, Date dateFin) {
		
		List<InfosAlimAutoCongesAnnuelsDto> result = new ArrayList<InfosAlimAutoCongesAnnuelsDto>();
		
		List<Spadmn> listSpAdmn = spadmnRepository.chercherListPositionAdmAgentSurPeriodeDonnee(helper.getMairieMatrFromIdAgent(idAgent), dateDebut, dateFin);
		
		if(null == listSpAdmn) {
			return result;
		}
		
		for(Spadmn spAdmn : listSpAdmn) {
			
			// On vérifie que la durée de la PA n’a pas dépassé la durée du droit à congés (en mois) 
			// vérifier les PA précédentes : si c’est une PA qui donne droit à congé mais sur une durée limitée, il faut cumuler les durées
			try {
				if(!checkDroitCongesAnnuels(spAdmn, dateFin)) {
					continue;
				}
			} catch (ParseException e) {
				logger.debug("Erreur ParseException : " + e.getMessage());
				return null;
			}
			
			// pour chaque PA, on regarde s'il n'y a pas plusieurs affectations avec code base different
			// si oui, on cree un nouvel enregistrement
			try {
				result.addAll(findBasesCongesForPA(spAdmn, idAgent, dateFin));
			} catch (ParseException e) {
				logger.debug("Erreur ParseException : " + e.getMessage());
				return null;
			}
		}
		
		return result;
	}
	
	protected boolean checkDroitCongesAnnuels(Spadmn spAdmn, Date dateFin) throws ParseException {
		
		// PA avec droit a conges
		if(null != spAdmn.getPositionAdministrative().getDroitConges()
				&& spAdmn.getPositionAdministrative().getDroitConges().trim().equals("N")) {
			return false;
		}
		// PA avec droit a conges mais duree limitee
		if(0 != spAdmn.getPositionAdministrative().getDuree()) {
			
			Date dateDebutPADroitLimite = sdfMairie.parse(spAdmn.getId().getDatdeb().toString());
			Date dateFinPADroitLimite = 0 != spAdmn.getDatfin() ? sdfMairie.parse(spAdmn.getDatfin().toString()) : dateFin;
			
			List<Spadmn> listSpAdmnAncien = spadmnRepository.chercherListPositionAdmAgentAncienne(spAdmn.getId().getNomatr(), spAdmn.getId().getDatdeb());
			
			if(null != listSpAdmnAncien) {
				for(Spadmn spAdmnAncien : listSpAdmnAncien) {
					
					if(0 == spAdmnAncien.getPositionAdministrative().getDuree()
							&& spAdmnAncien.getPositionAdministrative().getDroitConges().equals("O")) {
						break;
					}
					dateDebutPADroitLimite = sdfMairie.parse(spAdmnAncien.getId().getDatdeb().toString());
				}
			}
			
			int dureePADroitLimite = calculMoisEntre2Dates(dateDebutPADroitLimite, dateFinPADroitLimite);
			if(dureePADroitLimite > spAdmn.getPositionAdministrative().getDuree()) {
				return false;
			}
		}
		return true;
	}
	
	protected int calculMoisEntre2Dates(Date dateDebut, Date dateFin) {
		
		Interval inputInterval = new Interval(new DateTime(dateDebut), new DateTime(dateFin));
		double jours = new Double(inputInterval.toPeriod().getWeeks()*7 + inputInterval.toPeriod().getDays()) / 30;
		return new Long(Math.round(inputInterval.toPeriod().getMonths() + inputInterval.toPeriod().getYears() * 12 + jours)).intValue();
	}
	
	protected List<InfosAlimAutoCongesAnnuelsDto> findBasesCongesForPA(Spadmn spAdmn, Integer idAgent, Date dateFin) throws ParseException {
		
		List<InfosAlimAutoCongesAnnuelsDto> result = new ArrayList<InfosAlimAutoCongesAnnuelsDto>();
		
		Date dateDebutPA = sdfMairie.parse(spAdmn.getId().getDatdeb().toString());
		Date dateFinPA = 0 != spAdmn.getDatfin() ? sdfMairie.parse(spAdmn.getDatfin().toString()) : dateFin;
		
		List<Affectation> listAff = affectationRepository.getListeAffectationsAgentByPeriode(idAgent, dateDebutPA, dateFinPA);
		
		if(null != listAff) {
			for(Affectation aff : listAff) {
				
				InfosAlimAutoCongesAnnuelsDto dto = new InfosAlimAutoCongesAnnuelsDto();
				
				dto.setDateDebut(aff.getDateDebutAff().before(dateDebutPA) ? dateDebutPA : aff.getDateDebutAff());
				dto.setDateFin(null != aff.getDateFinAff() && aff.getDateFinAff().after(dateFinPA) ? dateFinPA : aff.getDateFinAff());
				dto.setIdBaseCongeAbsence(aff.getIdBaseHoraireAbsence());
				dto.setDroitConges(null != spAdmn.getPositionAdministrative().getDroitConges()
						&& spAdmn.getPositionAdministrative().getDroitConges().trim().equals("O"));
				dto.setIdAgent(idAgent);
				
				result.add(dto);
			}
		}
		
		return result;
	}
}
