package nc.noumea.mairie.web.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nc.noumea.mairie.model.bean.sirh.Affectation;
import nc.noumea.mairie.model.bean.sirh.PrimePointageAff;
import nc.noumea.mairie.ws.dto.RefPrimeDto;

public class AffectationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer idAffectation;
	private Integer idAgent;
	private Date dateDebut;
	private Date dateFin;
	private List<RefPrimeDto> listPrimesAff;
	private RefTypeSaisiCongeAnnuelDto baseConge;
	
	public AffectationDto() {
		this.listPrimesAff = new ArrayList<RefPrimeDto>();
	}
	
	public AffectationDto(Affectation aff, RefTypeSaisiCongeAnnuelDto baseConge) {
		this();
		this.idAffectation = aff.getIdAffectation();
		this.idAgent = aff.getAgent().getIdAgent();
		this.dateDebut = aff.getDateDebutAff();
		this.dateFin = aff.getDateFinAff();
		this.baseConge = baseConge;

		if(null != aff.getPrimePointageAff()) {
			for(PrimePointageAff prime : aff.getPrimePointageAff()) {
				RefPrimeDto primeDto = new RefPrimeDto();
				primeDto.setNumRubrique(prime.getPrimePointageAffPK().getNumRubrique());
				this.listPrimesAff.add(primeDto);
			}
		}
	}
	
	public Integer getIdAffectation() {
		return idAffectation;
	}
	public void setIdAffectation(Integer idAffectation) {
		this.idAffectation = idAffectation;
	}
	public Integer getIdAgent() {
		return idAgent;
	}
	public void setIdAgent(Integer idAgent) {
		this.idAgent = idAgent;
	}
	public Date getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}
	public Date getDateFin() {
		return dateFin;
	}
	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}
	
	public List<RefPrimeDto> getListPrimesAff() {
		return listPrimesAff;
	}

	public void setListPrimesAff(List<RefPrimeDto> listPrimesAff) {
		this.listPrimesAff = listPrimesAff;
	}

	public RefTypeSaisiCongeAnnuelDto getBaseConge() {
		return baseConge;
	}
	public void setBaseConge(RefTypeSaisiCongeAnnuelDto baseConge) {
		this.baseConge = baseConge;
	}
}
