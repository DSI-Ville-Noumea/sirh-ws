package nc.noumea.mairie.service.eae;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.Siserv;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.bean.Spmtsr;
import nc.noumea.mairie.model.bean.sirh.Activite;
import nc.noumea.mairie.model.bean.sirh.ActiviteFP;
import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.Agent;
import nc.noumea.mairie.model.bean.sirh.AutreAdministrationAgent;
import nc.noumea.mairie.model.bean.sirh.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.sirh.Competence;
import nc.noumea.mairie.model.bean.sirh.CompetenceFP;
import nc.noumea.mairie.model.bean.sirh.DiplomeAgent;
import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.bean.sirh.FormationAgent;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.model.repository.ISpcarrRepository;
import nc.noumea.mairie.model.repository.sirh.IAffectationRepository;
import nc.noumea.mairie.model.repository.sirh.IAgentRepository;
import nc.noumea.mairie.model.repository.sirh.IFichePosteRepository;
import nc.noumea.mairie.model.repository.sirh.ISirhRepository;
import nc.noumea.mairie.service.ISpCarrService;
import nc.noumea.mairie.service.ISpadmnService;
import nc.noumea.mairie.web.dto.AgentDto;
import nc.noumea.mairie.web.dto.AutreAdministrationAgentDto;
import nc.noumea.mairie.web.dto.CalculEaeInfosDto;
import nc.noumea.mairie.web.dto.DateAvctDto;
import nc.noumea.mairie.web.dto.DiplomeDto;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.FichePosteDto;
import nc.noumea.mairie.web.dto.FormationDto;
import nc.noumea.mairie.web.dto.ParcoursProDto;
import nc.noumea.mairie.web.dto.PositionAdmAgentDto;
import nc.noumea.mairie.web.dto.TitrePosteDto;
import nc.noumea.mairie.ws.IADSWSConsumer;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CalculEaeService implements ICalculEaeService {

	@Autowired
	private IMairieRepository mairieRepository;

	@Autowired
	private ISpcarrRepository spcarrRepository;

	@Autowired
	private ISirhRepository sirhRepository;

	@Autowired
	private IAffectationRepository affectationRepository;

	@Autowired
	private IFichePosteRepository fichePosteRepository;

	@Autowired
	private IAgentRepository agentRepository;

	@Autowired
	private ISpadmnService spadmnService;

	@Autowired
	private ISpCarrService spCarrService;

	@Autowired
	private IADSWSConsumer adsWSConsumer;

	@Autowired
	private IFichePosteRepository fichePosteDao;
	
	private Logger logger = LoggerFactory.getLogger(CalculEaeService.class);

	@Override
	public CalculEaeInfosDto getAffectationActiveByAgent(Integer idAgent, Integer anneeFormation) throws NumberFormatException, ParseException {

		Affectation affectation = affectationRepository.getAffectationActiveByAgentPourEAE(idAgent);

		CalculEaeInfosDto dto = new CalculEaeInfosDto();

		if (null != affectation && affectation.getFichePoste() != null && affectation.getFichePoste().getIdServiceADS() != null && affectation.getFichePoste().getTitrePoste() != null) {

			dto.setDateDebut(affectation.getDateDebutAff());
			dto.setDateFin(affectation.getDateFinAff());

			EntiteDto direction = adsWSConsumer.getAffichageDirection(affectation.getFichePoste().getIdServiceADS());
			EntiteDto service = adsWSConsumer.getAffichageService(affectation.getFichePoste().getIdServiceADS());
			EntiteDto section = adsWSConsumer.getAffichageSection(affectation.getFichePoste().getIdServiceADS());

			TitrePosteDto titrePoste = new TitrePosteDto();
			titrePoste.setLibTitrePoste(affectation.getFichePoste().getTitrePoste().getLibTitrePoste());

			List<Activite> listActi = new ArrayList<Activite>();
			if (affectation.getFichePoste().getActivites() != null) {
				for (ActiviteFP actiFP : affectation.getFichePoste().getActivites()) {
					Activite acti = fichePosteDao.chercherActivite(actiFP.getActiviteFPPK().getIdActivite());
					if (acti != null)
						listActi.add(acti);
				}
			}
			List<Competence> listComp = new ArrayList<Competence>();
			if (affectation.getFichePoste().getCompetencesFDP() != null) {
				for (CompetenceFP compFP : affectation.getFichePoste().getCompetencesFDP()) {
					Competence comp = fichePosteDao.chercherCompetence(compFP.getCompetenceFPPK().getIdCompetence());
					if (comp != null)
						listComp.add(comp);
				}
			}

			FichePosteDto fichePostePrincipale = new FichePosteDto(affectation.getFichePoste(), direction == null ? null : direction.getLabel(), service == null ? null : service.getLabel(),
					section == null ? null : section.getLabel(), null, listActi, listComp);
			fichePostePrincipale.setTitrePoste(titrePoste);

			dto.setFichePostePrincipale(fichePostePrincipale);
			if (null != affectation.getFichePosteSecondaire()) {
				EntiteDto serviceSecondaire = adsWSConsumer.getEntiteByIdEntite(affectation.getFichePosteSecondaire().getIdServiceADS());
				EntiteDto directionSecondaire = adsWSConsumer.getAffichageDirection(affectation.getFichePosteSecondaire().getIdServiceADS());
				EntiteDto sectionSecondaire = adsWSConsumer.getAffichageSection(affectation.getFichePosteSecondaire().getIdServiceADS());

				List<Activite> listActiSecondaire = new ArrayList<Activite>();
				if (affectation.getFichePosteSecondaire().getActivites() != null) {
					for (ActiviteFP actiFP : affectation.getFichePosteSecondaire().getActivites()) {
						Activite acti = fichePosteDao.chercherActivite(actiFP.getActiviteFPPK().getIdActivite());
						if (acti != null)
							listActiSecondaire.add(acti);
					}
				}
				List<Competence> listCompSecondaire = new ArrayList<Competence>();
				if (affectation.getFichePosteSecondaire().getCompetencesFDP() != null) {
					for (CompetenceFP compFP : affectation.getFichePosteSecondaire().getCompetencesFDP()) {
						Competence comp = fichePosteDao.chercherCompetence(compFP.getCompetenceFPPK().getIdCompetence());
						if (comp != null)
							listCompSecondaire.add(comp);
					}
				}

				FichePosteDto fichePosteSecondaire = new FichePosteDto(affectation.getFichePosteSecondaire(), directionSecondaire == null ? null : directionSecondaire.getLabel(),
						serviceSecondaire.getLabel(), sectionSecondaire == null ? null : sectionSecondaire.getLabel(), null, listActiSecondaire, listCompSecondaire);
				dto.setFichePosteSecondaire(fichePosteSecondaire);
			}

			if (null != affectation.getFichePoste().getSuperieurHierarchique()) {
				FichePoste fichePosteSuperieur = affectation.getFichePoste().getSuperieurHierarchique();
				FichePosteDto fichePosteResponsable = new FichePosteDto(fichePosteSuperieur.getIdServiceADS(), fichePosteSuperieur.getIdFichePoste(), fichePosteSuperieur.getAgent());
				if (null != fichePosteSuperieur.getTitrePoste()) {
					TitrePosteDto titrePosteResponable = new TitrePosteDto();
					titrePosteResponable.setLibTitrePoste(fichePosteSuperieur.getTitrePoste().getLibTitrePoste());
					fichePosteResponsable.setTitrePoste(titrePosteResponable);
				}
				dto.setFichePosteResponsable(fichePosteResponsable);
			}
		}

		Agent agent = agentRepository.getAgent(idAgent);

		if (null != agent) {
			dto.setListDiplome(getListDiplomeDto(idAgent));
			dto.setListParcoursPro(getListParcoursPro(idAgent, agent.getNomatr()));
			dto.setListFormation(getListFormation(idAgent, anneeFormation));
			dto.setPositionAdmAgentEnCours(spadmnService.chercherPositionAdmAgentEnCours(agent.getNomatr()));
			dto.setPositionAdmAgentAncienne(spadmnService.chercherPositionAdmAgentAncienne(agent.getNomatr()));
			dto.setCarriereFonctionnaireAncienne(spCarrService.getCarriereFonctionnaireAncienne(agent.getNomatr()));
			dto.setCarriereActive(spCarrService.getCarriereActive(agent.getNomatr()));
			if (dto.getCarriereActive().getGrade() != null && dto.getCarriereActive().getCodeCategorie() != null) {
				dto.setCarriereAncienneDansGrade(spCarrService.getCarriereAvecGrade(agent.getNomatr(), dto.getCarriereActive().getGrade().getCodeGrade(), dto.getCarriereActive().getCodeCategorie()));
			}
		}

		return dto;
	}

	@Override
	public List<DiplomeDto> getListDiplomeDto(Integer idAgent) {
		List<DiplomeDto> listDiplomeDto = new ArrayList<DiplomeDto>();
		List<DiplomeAgent> lstDiplome = sirhRepository.getListDiplomeByAgent(idAgent);
		if (null != lstDiplome) {
			for (DiplomeAgent diplome : lstDiplome) {
				DiplomeDto diplomeDto = new DiplomeDto(diplome);
				listDiplomeDto.add(diplomeDto);
			}
		}

		return listDiplomeDto;
	}

	protected List<ParcoursProDto> getListParcoursPro(Integer idAgent, Integer noMatr) throws NumberFormatException, ParseException {
		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");

		List<ParcoursProDto> listParcoursPro = new ArrayList<ParcoursProDto>();

		// suite à la nouvelle gestion des services, on cherche d'abord dans
		// l'affectation pour avoir une information exacte de service
		Affectation derniereAff = null;
		List<Affectation> listAff = affectationRepository.getListeAffectationsAgentOrderByDateAsc(idAgent);

		if (listAff.size() > 0) {
			derniereAff = listAff.get(0);
		}

		// on fait les parcours pro vis à vis des affectations
		List<ParcoursProDto> listParcoursProAff = getListeParcoursAff(listAff, idAgent);
		for (ParcoursProDto pAff : listParcoursProAff) {
			if (!listParcoursPro.contains(pAff))
				listParcoursPro.add(pAff);
		}

		// on cherche dans SPMTSR pour l'historique
		ArrayList<Spmtsr> listSpmtsr = new ArrayList<Spmtsr>();

		if (derniereAff == null) {
			listSpmtsr = (ArrayList<Spmtsr>) mairieRepository.getListSpmtsr(noMatr);
		} else {
			listSpmtsr = (ArrayList<Spmtsr>) mairieRepository.listerSpmtsrAvecAgentAPartirDateOrderDateDeb(noMatr, new Integer(sdfMairie.format(derniereAff.getDateDebutAff())));
		}
		// on fait les parcours pro vis à vis des affectations
		List<ParcoursProDto> listParcoursProSpmtsr = getListeParcoursSpmtsr(listSpmtsr, noMatr);
		for (ParcoursProDto pSpmtsr : listParcoursProSpmtsr) {
			if (!listParcoursPro.contains(pSpmtsr))
				listParcoursPro.add(pSpmtsr);
		}

		Collections.sort(listParcoursPro, new EaeParcoursProByDateDebutInverseComparator());
		return listParcoursPro;
	}

	private List<ParcoursProDto> getListeParcoursSpmtsr(ArrayList<Spmtsr> listSpmtsr, Integer noMatr) throws ParseException {
		List<ParcoursProDto> listParcoursPro = new ArrayList<ParcoursProDto>();
		SimpleDateFormat sdfMairie = new SimpleDateFormat("yyyyMMdd");

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for (int i = 0; i < listSpmtsr.size(); i++) {
			Spmtsr sp = listSpmtsr.get(i);
			EntiteDto service = adsWSConsumer.getEntiteByCodeServiceSISERV(sp.getId().getServi());
			EntiteDto direction = null;
			if (service != null && service.getIdEntite() != null) {
				direction = adsWSConsumer.getAffichageDirection(service.getIdEntite());
			}

			if (sp.getDatfin() == null || sp.getDatfin() == 0) {
				// on crée une ligne pour affectation
				String anneeDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(0, 4);
				String moisDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(4, 6);
				String jourDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(6, 8);
				String dateDebSpmtsr = jourDateDebSpmtsr + "/" + moisDateDebSpmtsr + "/" + anneeDateDebSpmtsr;

				ParcoursProDto parcoursProDto = new ParcoursProDto();
				parcoursProDto.setDateDebut(sdf.parse(dateDebSpmtsr));
				Date dateFinSp = null;
				if (sp.getDatfin() != null && sp.getDatfin() != 0) {
					String anneeDateFinSpmtsr = sp.getDatfin().toString().substring(0, 4);
					String moisDateFinSpmtsr = sp.getDatfin().toString().substring(4, 6);
					String jourDateFinSpmtsr = sp.getDatfin().toString().substring(6, 8);
					String dateFinSpmtsr = jourDateFinSpmtsr + "/" + moisDateFinSpmtsr + "/" + anneeDateFinSpmtsr;
					dateFinSp = sdf.parse(dateFinSpmtsr);
				}

				parcoursProDto.setDateFin(dateFinSp);
				parcoursProDto.setService(service == null ? "" : service.getLabel());
				parcoursProDto.setDirection(direction == null ? "" : direction.getLabel());
				if (!listParcoursPro.contains(parcoursProDto))
					listParcoursPro.add(parcoursProDto);
			} else {
				// on regarde si il y a des lignes suivantes
				Spmtsr spSuiv = mairieRepository.chercherSpmtsrAvecAgentEtDateDebut(noMatr,
						new Integer(sdfMairie.format(new DateTime(sdfMairie.parse(sp.getDatfin().toString())).plusDays(1).toDate())));
				if (spSuiv == null) {
					// on crée une ligne pour administration
					ParcoursProDto parcoursProDto = new ParcoursProDto();
					String anneeDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(0, 4);
					String moisDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(4, 6);
					String jourDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(6, 8);
					String dateDebSpmtsr = jourDateDebSpmtsr + "/" + moisDateDebSpmtsr + "/" + anneeDateDebSpmtsr;
					parcoursProDto.setDateDebut(sdf.parse(dateDebSpmtsr));
					Date dateFinSp = null;
					if (sp.getDatfin() != null && sp.getDatfin() != 0) {
						String anneeDateFinSpmtsr = sp.getDatfin().toString().substring(0, 4);
						String moisDateFinSpmtsr = sp.getDatfin().toString().substring(4, 6);
						String jourDateFinSpmtsr = sp.getDatfin().toString().substring(6, 8);
						String dateFinSpmtsr = jourDateFinSpmtsr + "/" + moisDateFinSpmtsr + "/" + anneeDateFinSpmtsr;
						dateFinSp = sdf.parse(dateFinSpmtsr);
					}
					parcoursProDto.setDateFin(dateFinSp);
					parcoursProDto.setService(service == null ? "" : service.getLabel());
					parcoursProDto.setDirection(direction == null ? "" : direction.getLabel());
					if (!listParcoursPro.contains(parcoursProDto))
						listParcoursPro.add(parcoursProDto);
				} else {
					if (spSuiv.getId().getServi().equals(sp.getId().getServi())) {
						i++;
						boolean fin = false;
						Integer dateSortie = null;
						if (spSuiv.getDatfin() == null || spSuiv.getDatfin() == 0) {
							fin = true;
						} else {
							Integer dateFinSP = Integer.valueOf(spSuiv.getDatfin());
							String anneeDateDebSpmtsr = dateFinSP.toString().substring(0, 4);
							String moisDateDebSpmtsr = dateFinSP.toString().substring(4, 6);
							String jourDateDebSpmtsr = dateFinSP.toString().substring(6, 8);
							String dateDebSpmtsr = anneeDateDebSpmtsr + moisDateDebSpmtsr + jourDateDebSpmtsr;
							dateSortie = Integer.valueOf(dateDebSpmtsr);
						}
						while (!fin) {
							spSuiv = mairieRepository.chercherSpmtsrAvecAgentEtDateDebut(noMatr,
									dateSortie == null ? 0 : new Integer(sdfMairie.format(new DateTime(sdfMairie.parse(dateSortie.toString())).plusDays(1).toDate())));
							if (spSuiv == null) {
								fin = true;
							} else {
								i++;
								try {
									Integer dateFinSP = Integer.valueOf(spSuiv.getDatfin());
									String anneeDateDebSpmtsr = dateFinSP.toString().substring(0, 4);
									String moisDateDebSpmtsr = dateFinSP.toString().substring(4, 6);
									String jourDateDebSpmtsr = dateFinSP.toString().substring(6, 8);
									String dateDebSpmtsr = anneeDateDebSpmtsr + moisDateDebSpmtsr + jourDateDebSpmtsr;
									dateSortie = Integer.valueOf(dateDebSpmtsr);
								} catch (Exception e) {
									fin = true;
								}
							}
						}
						// on crée la ligne
						ParcoursProDto parcoursProDto = new ParcoursProDto();
						String anneeDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(0, 4);
						String moisDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(4, 6);
						String jourDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(6, 8);
						String dateDebSpmtsr = jourDateDebSpmtsr + "/" + moisDateDebSpmtsr + "/" + anneeDateDebSpmtsr;
						parcoursProDto.setDateDebut(sdf.parse(dateDebSpmtsr));
						parcoursProDto.setDateFin(dateSortie == null ? null : new SimpleDateFormat("yyyyMMdd").parse(dateSortie.toString()));
						parcoursProDto.setService(service == null ? "" : service.getLabel());
						parcoursProDto.setDirection(direction == null ? "" : direction.getLabel());
						if (!listParcoursPro.contains(parcoursProDto))
							listParcoursPro.add(parcoursProDto);
					} else {

						// on crée une ligne pour administration
						ParcoursProDto parcoursProDto = new ParcoursProDto();
						String anneeDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(0, 4);
						String moisDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(4, 6);
						String jourDateDebSpmtsr = sp.getId().getDatdeb().toString().substring(6, 8);
						String dateDebSpmtsr = jourDateDebSpmtsr + "/" + moisDateDebSpmtsr + "/" + anneeDateDebSpmtsr;
						parcoursProDto.setDateDebut(sdf.parse(dateDebSpmtsr));
						Date dateFinSp = null;
						if (sp.getDatfin() != null && sp.getDatfin() != 0) {
							String anneeDateFinSpmtsr = sp.getDatfin().toString().substring(0, 4);
							String moisDateFinSpmtsr = sp.getDatfin().toString().substring(4, 6);
							String jourDateFinSpmtsr = sp.getDatfin().toString().substring(6, 8);
							String dateFinSpmtsr = jourDateFinSpmtsr + "/" + moisDateFinSpmtsr + "/" + anneeDateFinSpmtsr;
							dateFinSp = sdf.parse(dateFinSpmtsr);
						}
						parcoursProDto.setDateFin(dateFinSp);
						parcoursProDto.setService(service == null ? "" : service.getLabel());
						parcoursProDto.setDirection(direction == null ? "" : direction.getLabel());
						if (!listParcoursPro.contains(parcoursProDto))
							listParcoursPro.add(parcoursProDto);
					}

				}
			}
		}
		return listParcoursPro;
	}

	private List<ParcoursProDto> getListeParcoursAff(List<Affectation> listAff, Integer idAgent) {
		List<ParcoursProDto> listParcoursPro = new ArrayList<ParcoursProDto>();
		for (int i = 0; i < listAff.size(); i++) {
			Affectation aff = listAff.get(i);
			FichePoste fp = fichePosteRepository.chercherFichePoste(aff.getFichePoste().getIdFichePoste());

			EntiteDto service = null;
			EntiteDto direction = null;
			if (fp == null || fp.getIdServiceADS() == null) {
				if (fp.getIdServi() == null) {
					continue;
				} else {
					Siserv siserv = mairieRepository.chercherSiserv(fp.getIdServi());
					if (siserv != null && siserv.getServi() != null) {
						service = new EntiteDto();
						service.setLabel(siserv.getLiserv());
						service.setSigle(siserv.getSigle());
					} else {
						continue;
					}
				}
			} else {
				service = adsWSConsumer.getEntiteByIdEntite(aff.getFichePoste().getIdServiceADS());
				direction = adsWSConsumer.getAffichageDirection(aff.getFichePoste().getIdServiceADS());
			}

			if (service == null && direction == null) {
				continue;
			}

			if (aff.getDateFinAff() == null) {
				// on crée une ligne pour affectation
				ParcoursProDto parcoursProDto = new ParcoursProDto();
				parcoursProDto.setDateDebut(aff.getDateDebutAff());
				parcoursProDto.setDateFin(aff.getDateFinAff());

				parcoursProDto.setService(service == null ? "" : service.getLabel());
				parcoursProDto.setDirection(direction == null ? "" : direction.getLabel());

				if (!listParcoursPro.contains(parcoursProDto))
					listParcoursPro.add(parcoursProDto);
			} else {
				// on regarde si il y a des lignes suivantes
				DateTime dateFin = new DateTime(aff.getDateFinAff());
				Affectation affSuiv = affectationRepository.chercherAffectationAgentAvecDateDebut(idAgent, dateFin.plusDays(1).toDate());
				if (affSuiv == null) {

					// on crée une ligne pour affectation
					ParcoursProDto parcoursProDto = new ParcoursProDto();
					parcoursProDto.setDateDebut(aff.getDateDebutAff());
					parcoursProDto.setDateFin(aff.getDateFinAff());

					parcoursProDto.setService(service == null ? "" : service.getLabel());
					parcoursProDto.setDirection(direction == null ? "" : direction.getLabel());

					if (!listParcoursPro.contains(parcoursProDto))
						listParcoursPro.add(parcoursProDto);
				} else {
					boolean traiteSuivant = false;
					// on verifie qu c'est sur le même service
					if (aff.getFichePoste().getIdServiceADS() == null) {
						if (affSuiv.getFichePoste().getIdServiceADS() == null) {
							if (aff.getFichePoste().getIdServi().equals(affSuiv.getFichePoste().getIdServi())) {
								traiteSuivant = true;
							}
						} else {
							if (aff.getFichePoste().getIdServi().equals(affSuiv.getFichePoste().getIdServi())) {
								traiteSuivant = true;
							}
						}
					} else {
						if (affSuiv.getFichePoste().getIdServiceADS() != null) {
							if (aff.getFichePoste().getIdServiceADS().toString().equals(affSuiv.getFichePoste().getIdServiceADS().toString())) {
								traiteSuivant = true;
							}
						}
					}

					if (traiteSuivant) {
						if (affSuiv.getFichePoste().getIdServiceADS() != null) {
							service = adsWSConsumer.getEntiteByIdEntite(affSuiv.getFichePoste().getIdServiceADS());
							direction = adsWSConsumer.getAffichageDirection(affSuiv.getFichePoste().getIdServiceADS());
						}
						i++;
						boolean fin = false;
						DateTime dateSortie = null;
						if (affSuiv.getDateFinAff() == null) {
							fin = true;
						} else {
							dateSortie = new DateTime(affSuiv.getDateFinAff());
						}

						while (!fin) {
							affSuiv = affectationRepository.chercherAffectationAgentAvecDateDebut(idAgent, dateSortie == null ? null : dateSortie.plusDays(1).toDate());

							if (affSuiv == null) {
								fin = true;
							} else {
								boolean traiteSuivantBoucle = false;
								// on verifie qu c'est sur le même service
								if (aff.getFichePoste().getIdServiceADS() == null) {
									if (affSuiv.getFichePoste().getIdServiceADS() == null) {
										if (aff.getFichePoste().getIdServi().equals(affSuiv.getFichePoste().getIdServi())) {
											traiteSuivantBoucle = true;
										}
									} else {
										if (aff.getFichePoste().getIdServi().equals(affSuiv.getFichePoste().getIdServi())) {
											traiteSuivantBoucle = true;
										}
									}
								} else {
									if (affSuiv.getFichePoste().getIdServiceADS() != null) {
										if (aff.getFichePoste().getIdServiceADS().toString().equals(affSuiv.getFichePoste().getIdServiceADS().toString())) {
											traiteSuivantBoucle = true;
										}
									}
								}

								if (traiteSuivantBoucle) {
									if (affSuiv.getFichePoste().getIdServiceADS() != null) {
										service = adsWSConsumer.getEntiteByIdEntite(affSuiv.getFichePoste().getIdServiceADS());
										direction = adsWSConsumer.getAffichageDirection(affSuiv.getFichePoste().getIdServiceADS());
									}
									i++;
									dateSortie = new DateTime(affSuiv.getDateFinAff());
								} else {
									fin = true;
								}

							}
						}

						// on crée une ligne pour affectation
						ParcoursProDto parcoursProDto = new ParcoursProDto();
						parcoursProDto.setDateDebut(aff.getDateDebutAff());
						parcoursProDto.setDateFin(dateSortie == null ? null : dateSortie.toDate());

						parcoursProDto.setService(service == null ? "" : service.getLabel());
						parcoursProDto.setDirection(direction == null ? "" : direction.getLabel());

						if (!listParcoursPro.contains(parcoursProDto))
							listParcoursPro.add(parcoursProDto);
					} else {
						// on crée une ligne pour affectation
						ParcoursProDto parcoursProDto = new ParcoursProDto();
						parcoursProDto.setDateDebut(aff.getDateDebutAff());
						parcoursProDto.setDateFin(aff.getDateFinAff());

						parcoursProDto.setService(service == null ? "" : service.getLabel());
						parcoursProDto.setDirection(direction == null ? "" : direction.getLabel());

						if (!listParcoursPro.contains(parcoursProDto))
							listParcoursPro.add(parcoursProDto);
					}
				}
			}
		}
		return listParcoursPro;
	}

	private List<FormationDto> getListFormation(Integer idAgent, Integer anneeFormation) {

		List<FormationDto> listFormation = new ArrayList<FormationDto>();

		List<FormationAgent> listFormationAgent = sirhRepository.getListFormationAgentByAnnee(idAgent, anneeFormation);
		if (null != listFormationAgent) {
			for (FormationAgent fa : listFormationAgent) {
				FormationDto formationDto = new FormationDto(fa);
				listFormation.add(formationDto);
			}
		}

		return listFormation;
	}

	@Override
	public List<CalculEaeInfosDto> getListeAffectationsAgentAvecService(Integer idAgent, Integer idServiceADS) {

		List<CalculEaeInfosDto> listDto = new ArrayList<CalculEaeInfosDto>();

		List<Affectation> listAffectation = affectationRepository.getListeAffectationsAgentAvecService(idAgent, idServiceADS);

		if (null != listAffectation) {
			for (Affectation affectation : listAffectation) {
				CalculEaeInfosDto dto = new CalculEaeInfosDto();
				dto.setDateDebut(affectation.getDateDebutAff());
				dto.setDateFin(affectation.getDateFinAff());
				listDto.add(dto);
			}
		}

		return listDto;
	}

	@Override
	public List<CalculEaeInfosDto> getListeAffectationsAgentAvecFP(Integer idAgent, Integer idFichePoste) {

		List<CalculEaeInfosDto> listDto = new ArrayList<CalculEaeInfosDto>();

		List<Affectation> listAffectation = affectationRepository.getListeAffectationsAgentAvecFP(idAgent, idFichePoste);

		if (null != listAffectation) {
			for (Affectation affectation : listAffectation) {
				CalculEaeInfosDto dto = new CalculEaeInfosDto();
				dto.setDateDebut(affectation.getDateDebutAff());
				dto.setDateFin(affectation.getDateFinAff());
				listDto.add(dto);
			}
		}

		return listDto;
	}

	@Override
	public List<AgentDto> getListeAgentEligibleEAESansAffectes() {

		List<Integer> listNoMatr = spcarrRepository.getListeCarriereActiveAvecPA();

		List<AgentDto> result = new ArrayList<AgentDto>();
		if (null != listNoMatr) {
			for (Integer noMatr : listNoMatr) {
				Agent agent = agentRepository.getAgentEligibleEAESansAffectes(noMatr);

				if (null != agent) {
					AgentDto dto = new AgentDto(agent);
					if (!result.contains(dto))
						result.add(dto);
				}
			}
		}

		return result;
	}

	@Override
	public List<AgentDto> getListeAgentEligibleEAEAffectes() {

		List<Integer> listNoMatr = spcarrRepository.getListeCarriereActiveAvecPAAffecte();

		List<AgentDto> result = new ArrayList<AgentDto>();
		if (null != listNoMatr) {
			for (Integer noMatr : listNoMatr) {
				Agent agent = agentRepository.getAgentWithListNomatr(noMatr);

				if (null != agent) {
					AgentDto dto = new AgentDto(agent);
					if (!result.contains(dto))
						result.add(dto);
				}
			}
		}

		return result;
	}

	@Override
	public AutreAdministrationAgentDto chercherAutreAdministrationAgentAncienne(Integer idAgent, boolean isFonctionnaire) {

		AutreAdministrationAgent autreAdministrationAgent = sirhRepository.chercherAutreAdministrationAgentAncienne(idAgent, isFonctionnaire);

		AutreAdministrationAgentDto result = null;
		if (null != autreAdministrationAgent) {
			result = new AutreAdministrationAgentDto(autreAdministrationAgent);
		}

		return result;
	}

	@Override
	public List<AutreAdministrationAgentDto> getListeAutreAdministrationAgent(Integer idAgent) {

		List<AutreAdministrationAgent> list = sirhRepository.getListeAutreAdministrationAgent(idAgent);

		List<AutreAdministrationAgentDto> result = new ArrayList<AutreAdministrationAgentDto>();
		if (null != list) {
			for (AutreAdministrationAgent aaa : list) {
				AutreAdministrationAgentDto dto = new AutreAdministrationAgentDto(aaa);
				result.add(dto);
			}
		}

		return result;
	}

	@Override
	public Integer getDernierAvancement(Integer idAgent) {
		AvancementFonctionnaire dernierAvct = sirhRepository.getDernierAvancement(idAgent);
		
		if (dernierAvct == null) {
			logger.warn("Aucun avancement trouvé pour l'agent matricule {}.", idAgent);
			return null;
		}
		
		if (dernierAvct.getAvisCapEmployeur() == null) {
			logger.warn("L'avis de l'employeur n'a pas été renseigné pour l'avancement id {}.", dernierAvct.getIdAvct());
			return null;
		}
		
		return dernierAvct.getAvisCapEmployeur().getIdAvisCap();
	}

	@Override
	public DateAvctDto getCalculDateAvancement(Integer idAgent) {
		DateAvctDto result = new DateAvctDto();
		Integer nomatr = Integer.valueOf(idAgent.toString().substring(3, idAgent.toString().length()));

		// on lance le calcul de l'avancement prev
		// on regarde de quelle categorie est l'agent (Fonctionnaire ou
		// Contractuel)
		Spcarr carr = spcarrRepository.getCarriereActive(nomatr);
		if (carr != null && carr.getCategorie() != null) {
			// on regarde si sa derniere PA n'est pas inactive
			PositionAdmAgentDto posAdmn = spadmnService.chercherPositionAdmAgentEnCours(nomatr);
			try {
				// on regarde si la position administrative est de type
				// numerique
				@SuppressWarnings("unused")
				Integer codePositionAdm = Integer.valueOf(posAdmn.getCdpadm());
				String codeCategorie = carr.getCategorie().getCodeCategorie().toString();
				if (codeCategorie.equals("4")) {
					// alors on est dans les contractuels
					// on ajoute 2 ans à la date de carriere
					SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
					Calendar cal = Calendar.getInstance();
					cal.setTime(sdf.parse(carr.getId().getDatdeb().toString()));
					cal.add(Calendar.YEAR, 2);
					result.setDateAvct(cal.getTime());
					return result;
				} else if (codeCategorie.equals("1") || codeCategorie.equals("2") || codeCategorie.equals("18") || codeCategorie.equals("20")) {
					// alors on est dans les fonctionnaires
					result.setDateAvct(performCalculFonctionnaire(carr, idAgent));
					return result;
				} else if (codeCategorie.equals("6") || codeCategorie.equals("16") || codeCategorie.equals("17") || codeCategorie.equals("19")) {
					// alors on est dans les détachés
					result.setDateAvct(performCalculFonctionnaire(carr, idAgent));
					return result;
				}

			} catch (Exception e) {
				// si non , alors on ne peut pas calculer la date d'avct
				return result;
			}
		}
		return result;
	}

	private Date performCalculFonctionnaire(Spcarr carr, Integer idAgent) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		// on regarde si il y a d'autre carrieres avec le meme grade
		// si oui on prend la carriere plus lointaine
		ArrayList<Spcarr> listeCarrMemeGrade = (ArrayList<Spcarr>) spcarrRepository.listerCarriereAvecGradeEtStatut(Integer.valueOf(idAgent.toString().substring(3, idAgent.toString().length())), carr
				.getGrade().getCdgrad(), carr.getCategorie().getCodeCategorie());
		if (listeCarrMemeGrade != null && listeCarrMemeGrade.size() > 0) {
			carr = (Spcarr) listeCarrMemeGrade.get(0);
		}

		Spgradn gradeActuel = carr.getGrade();
		// Si pas de grade suivant, agent non éligible
		if (gradeActuel.getGradeSuivant() != null && null != gradeActuel.getGradeSuivant().getCdgrad() && !"".equals(gradeActuel.getGradeSuivant().getCdgrad().trim())) {

			if ((carr.getCategorie().getCodeCategorie().toString().equals("2") || carr.getCategorie().getCodeCategorie().toString().equals("18")) && gradeActuel.getDureeMoyenne() != null
					&& (!gradeActuel.getDureeMoyenne().toString().equals("12"))) {
				// si stagiaire
				// la date d'avancement est la meme +1an.
				Calendar cal2 = Calendar.getInstance();
				cal2.setTime(sdf.parse(carr.getId().getDatdeb().toString()));
				cal2.add(Calendar.YEAR, 1);
				return cal2.getTime();

			}

			// calcul BM/ACC applicables
			int nbJoursBM = calculJourBM(gradeActuel, carr);
			int nbJoursACC = calculJourACC(gradeActuel, carr);

			int nbJoursBonus = nbJoursBM + nbJoursACC;

			// Calcul date avancement au Grade actuel
			if (gradeActuel.getDureeMoyenne() != null) {
				if (nbJoursBonus > (gradeActuel.getDureeMoyenne() * 30)) {
					return sdf.parse(carr.getId().getDatdeb().toString());
				} else {
					return calculDateAvctMoy(gradeActuel, carr);
				}
			}
		} else {
			return null;
		}
		return null;
	}

	private Date calculDateAvctMoy(Spgradn gradeActuel, Spcarr carr) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Calendar calSansBmAcc = Calendar.getInstance();
		calSansBmAcc.setTime(sdf.parse(carr.getId().getDatdeb().toString()));
		calSansBmAcc.add(Calendar.MONTH, gradeActuel.getDureeMoyenne());

		Date dateSansAccBm = calSansBmAcc.getTime();

		Integer annee = carr.getAccAnnee() + carr.getBmAnnee();
		Calendar calAvcAnne = Calendar.getInstance();
		calAvcAnne.setTime(dateSansAccBm);
		calAvcAnne.add(Calendar.YEAR, -annee);

		Date dateAvcAnne = calAvcAnne.getTime();

		Integer mois = carr.getAccMois() + carr.getBmMois();
		Calendar calAvcMois = Calendar.getInstance();
		calAvcMois.setTime(dateAvcAnne);
		calAvcMois.add(Calendar.MONTH, -mois);

		Date dateAvcMois = calAvcMois.getTime();

		Integer jour = carr.getAccJour() + carr.getBmJour();
		Calendar calAvcJour = Calendar.getInstance();
		calAvcJour.setTime(dateAvcMois);
		calAvcJour.add(Calendar.DAY_OF_MONTH, -jour);

		Date dateAvcJour = calAvcJour.getTime();

		return dateAvcJour;
	}

	private int calculJourACC(Spgradn gradeActuel, Spcarr carr) {
		Integer nbJoursACC = 0;
		if (gradeActuel.getAcc() != null && gradeActuel.getAcc().equals("O")) {
			nbJoursACC += (carr.getAccAnnee() * 360) + (carr.getAccMois() * 30) + carr.getAccJour();
		}
		return nbJoursACC;
	}

	private int calculJourBM(Spgradn gradeActuel, Spcarr carr) {
		Integer nbJoursBM = 0;
		if (gradeActuel.getBm() != null && gradeActuel.getBm().equals("O")) {
			nbJoursBM = (carr.getBmAnnee() * 360) + (carr.getBmMois() * 30) + carr.getBmJour();
		}
		return nbJoursBM;
	}
}
