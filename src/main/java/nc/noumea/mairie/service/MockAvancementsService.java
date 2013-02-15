package nc.noumea.mairie.service;

import java.util.List;

import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.web.dto.avancements.AvancementDifferencieDto;
import nc.noumea.mairie.web.dto.avancements.AvancementDifferencieItemDto;
import nc.noumea.mairie.web.dto.avancements.ChangementClasseDto;
import nc.noumea.mairie.web.dto.avancements.ChangementClasseItemDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementCorpsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

import org.joda.time.DateTime;

//@Service
public class MockAvancementsService implements IAvancementsService {

	@Override
	public CommissionAvancementDto getCommissionsForCapAndCadreEmploi(int idCap, int idCadreEmploi) {

		CommissionAvancementDto result = new CommissionAvancementDto();
		result.getCommissionsParCorps().add(new CommissionAvancementCorpsDto());
		CommissionAvancementCorpsDto dto = result.getCommissionsParCorps().get(0);

		dto.setAvancementsDifferencies(new AvancementDifferencieDto());
		AvancementDifferencieDto avctDifdto = dto.getAvancementsDifferencies();
		avctDifdto.setAnnee(2012);
		avctDifdto.setDeliberationLibelle("Statut particulier des cadres d'emploi des personnels de la filière administrative des communes de la Nouvelle Calédonie et de leurs étabblissements publics");
		avctDifdto.setCadreEmploiLibelle("Cadre d'emploi des attachés d'administration");
		avctDifdto.setCap("1");
		avctDifdto.setFiliere("Filière OUF");
		avctDifdto.setCategorie("A");
		avctDifdto.setEmployeur("Ville de Nouméa");
		avctDifdto.setNbAgents(12);
		avctDifdto.setQuotaAvancementDureeMinimale(4);
		avctDifdto.setPresident("M. Pierre Président");
		avctDifdto.getEmployeurs().add("M. Jean LEQUES,\nMaire de la ville de Nouméa ou son représentant");
		avctDifdto.getEmployeurs().add("M. Gilbert TY UIENON,\nReprésentant l'association des Maires de Nouvelle Calédonie ou son représentant");
		avctDifdto.getEmployeurs().add("MM. Georges NATUREL ou Jean-Luc CHEVALIER,\nReprésentant de l'association française des maires de Nouvelle Calédonie");
		avctDifdto.getRepresentants().add("MERIADEC Steven");
		avctDifdto.getRepresentants().add("PITOUT Cédric");
		avctDifdto.getRepresentants().add("HOFFSCHIR Hortensia");
		avctDifdto.getAvancementDifferencieItems().add(new AvancementDifferencieItemDto());
		
		AvancementDifferencieItemDto avctDifItemdto1 = avctDifdto.getAvancementDifferencieItems().get(0);
		avctDifItemdto1.setNom("LAURIENT");
		avctDifItemdto1.setPrenom("Michel");
		avctDifItemdto1.setGrade("NORMAL");
		avctDifItemdto1.setDatePrevisionnelleAvancement(new DateTime(2050, 02, 17, 0, 0, 0).toDate());
		avctDifItemdto1.setDureeMax(true);
		avctDifItemdto1.setDureeMoy(false);
		avctDifItemdto1.setDureeMin(false);
		avctDifItemdto1.setClassement(50);
		avctDifdto.getAvancementDifferencieItems().add(new AvancementDifferencieItemDto());
		
		AvancementDifferencieItemDto avctDifItemdto2 = avctDifdto.getAvancementDifferencieItems().get(1);
		avctDifItemdto2.setNom("CHARVET");
		avctDifItemdto2.setPrenom("Tatiana");
		avctDifItemdto2.setGrade("NORMALE");
		avctDifItemdto2.setDatePrevisionnelleAvancement(new DateTime(2017, 06, 27, 0, 0, 0).toDate());
		avctDifItemdto2.setDureeMax(false);
		avctDifItemdto2.setDureeMoy(true);
		avctDifItemdto2.setDureeMin(false);
		avctDifItemdto2.setClassement(23);
		

		dto.setChangementClasses(new ChangementClasseDto());
		ChangementClasseDto chgClasseDto = dto.getChangementClasses();
		chgClasseDto.setA("La chaine qu'on sait pas ce que c'est ...");
		chgClasseDto.setCap("4");
		chgClasseDto.setPresident("MME. PREMIERE Dame");
		chgClasseDto.getMembresPresents().add("Le maire de la ville de Nouméa ou son représentant");
		chgClasseDto.getMembresPresents().add("Le président de l'association des maires de Nouvelle Calédonie ou son représentant");
		chgClasseDto.getMembresPresents().add("La présidente de l'association française des maires de Nouvelle Calédonie ou son représentant");
		chgClasseDto.getRepresentants().add("GOUASSEM Steeve");
		chgClasseDto.getRepresentants().add("HARBULOT Franck");
		chgClasseDto.getRepresentants().add("STIRRUP Robert");
		
		chgClasseDto.getChangementClasseItems().add(new ChangementClasseItemDto());
		ChangementClasseItemDto chgt1 = chgClasseDto.getChangementClasseItems().get(0);
		chgt1.setNom("LAURIENT");
		chgt1.setPrenom("Michel");
		chgt1.setCorps("BRAS");
		chgt1.setClasseActuelle("CE");
		chgt1.setEchelonActuel("2");
		chgt1.setDateEffet(new DateTime(2012, 9, 10, 0, 0, 0).toDate());
		chgt1.setACC("?ACC?");
		chgt1.setClasseProposee("CM");
		chgt1.setEchelonPropose("1");
		chgt1.setDateProposee(new DateTime(2013, 9, 10, 0, 0, 0).toDate());
		chgt1.setAvisEmployeur("OK mais ca passe jsute cette année!");
		
		chgClasseDto.getChangementClasseItems().add(new ChangementClasseItemDto());
		ChangementClasseItemDto chgt2 = chgClasseDto.getChangementClasseItems().get(1);
		chgt2.setNom("CHARVET");
		chgt2.setPrenom("Tatiana");
		chgt2.setCorps("TETE");
		chgt2.setClasseActuelle("CM");
		chgt2.setEchelonActuel("2");
		chgt2.setDateEffet(new DateTime(2012, 9, 10, 0, 0, 0).toDate());
		chgt2.setACC("?ACC?");
		chgt2.setClasseProposee("6E");
		chgt2.setEchelonPropose("1");
		chgt2.setDateProposee(new DateTime(2013, 9, 10, 0, 0, 0).toDate());
		chgt2.setAvisEmployeur("OK bon boulot!");

		return result;
	}

	@Override
	public List<Agent> getAgentsForCommission(int idCap, int idCadreEmploi) {
		// TODO Auto-generated method stub
		return null;
	}

}
