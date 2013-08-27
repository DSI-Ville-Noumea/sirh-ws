package nc.noumea.mairie.service;

import java.text.ParseException;
import java.util.List;

import nc.noumea.mairie.model.bean.AvancementFonctionnaire;
import nc.noumea.mairie.model.bean.Cap;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.web.dto.avancements.ArreteListDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementCorpsDto;
import nc.noumea.mairie.web.dto.avancements.CommissionAvancementDto;

public interface IAvancementsService {

	public CommissionAvancementDto getCommissionsForCapAndCadreEmploi(
			int idCap, int idCadreEmploi, boolean avisEAE);

	public List<String> getAvancementsEaesForCapAndCadreEmploi(int idCap,
			int idCadreEmploi);

	public ArreteListDto getArretesForUsers(String csvIdAgents,
			boolean isChangmentClasse, int year) throws ParseException;

	public List<AvancementFonctionnaire> getAvancementsForArretes(
			List<Integer> agentIds, int year);

	public List<AvancementFonctionnaire> getAvancementsForCommission(int annee,
			int idCap, String corps, List<Integer> codesCategories);

	public List<Spgeng> getCorpsForCadreEmploi(int idCadreEmploi);

	public CommissionAvancementCorpsDto createCommissionCorps(Cap cap,
			Spgeng spgeng, List<AvancementFonctionnaire> avancements,
			boolean avisEAE);

	public Cap getCap(int idCap);
}
