package nc.noumea.mairie.model.bean;

import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

import nc.noumea.mairie.model.bean.sirh.FichePoste;
import nc.noumea.mairie.model.pk.SppostId;

@Entity
@Table(name = "SPPOST")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class Sppost {

	@Override
	public String toString() {
		return "Sppost [id=" + id + "]";
	}

	@Id
	private SppostId id;

	@Column(name = "CDLIEU", columnDefinition = "decimal")
	private Integer cdlieu;

	@Column(name = "POSERV", columnDefinition = "char")
	private String poserv;

	@Column(name = "CTITRE", columnDefinition = "numeric")
	private Integer ctitre;

	@Column(name = "POETUD", columnDefinition = "numeric")
	private Integer poetud;

	@Column(name = "CRESPO", columnDefinition = "numeric")
	private Integer crespo;

	@Column(name = "POGRAD", columnDefinition = "char")
	private String pograd;

	@Column(name = "POMIS1")
	private String pomis1;

	@Column(name = "POMIS2")
	private String pomis2;

	@Column(name = "POMIS3")
	private String pomis3;

	@Column(name = "POMIS4")
	private String pomis4;

	@Column(name = "POMATR", columnDefinition = "numeric")
	private Integer pomatr;

	@Column(name = "POCOND", columnDefinition = "char")
	private String pocond;

	@Column(name = "PODVAL", columnDefinition = "numeric")
	private Integer podval;

	@Column(name = "PODSUP", columnDefinition = "numeric")
	private Integer podsup;

	@Column(name = "POSERP", columnDefinition = "char")
	private String poserp;

	@Column(name = "CODFON", columnDefinition = "char")
	private String codfon;

	@Column(name = "CODACT", columnDefinition = "char")
	private String codact;

	@Column(name = "NOACTI", columnDefinition = "char")
	private String noacti;

	@Column(name = "REGLEM", columnDefinition = "numeric")
	private Integer reglem;

	@Column(name = "COMMEN", columnDefinition = "char")
	private String commen;

	@Column(name = "BUDGET", columnDefinition = "numeric")
	private Integer budget;

	@Column(name = "SOANNE", columnDefinition = "numeric")
	private Integer soanne;

	@Column(name = "SONUOR", columnDefinition = "numeric")
	private Integer sonuor;

	@Column(name = "PRIMAIRE", columnDefinition = "numeric")
	private Integer primaire;

	public Sppost() {
		super();
	}

	public Sppost(FichePoste fichePoste) {
		super();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		SppostId idSppost = new SppostId();
		idSppost.setPoanne(new Integer(fichePoste.getNumFP().substring(0, 4)));
		idSppost.setPonuor(new Integer(fichePoste.getNumFP().substring(5, fichePoste.getNumFP().length())));
		this.id = idSppost;
		this.poserv = fichePoste.getIdServi();
		this.podval = fichePoste.getDateDebutValiditeFp() == null ? 0 : new Integer(sdf.format(
				fichePoste.getDateDebutValiditeFp()).replace("-", ""));
		this.podsup = fichePoste.getDateFinValiditeFp() == null ? 0 : new Integer(sdf.format(
				fichePoste.getDateFinValiditeFp()).replace("-", ""));
		this.codfon = fichePoste.getNfa();
		this.noacti = fichePoste.getOpi() == null ? "" : fichePoste.getOpi();
		this.reglem = fichePoste.getReglementaire() == null ? 0 : fichePoste.getReglementaire().getCdThor();
		this.budget = fichePoste.getBudgete() == null ? 0 : fichePoste.getBudgete().getCdThor();
		this.codact = "A";

		// champ non utilise mais mis a zero car valeur indefinies non admises
		this.cdlieu = 0;
		this.ctitre = 0;
		this.poetud = 0;
		this.crespo = 0;
		this.pograd = "";
		this.pomis1 = "";
		this.pomis2 = "";
		this.pomis3 = "";
		this.pomis4 = "";
		this.pomatr = 0;
		this.pocond = "";
		this.podval = 0;
		this.poserp = "";
		this.commen = "";
		this.primaire = 0;
	}

	public SppostId getId() {
		return id;
	}

	public void setId(SppostId id) {
		this.id = id;
	}

	public Integer getCdlieu() {
		return cdlieu;
	}

	public void setCdlieu(Integer cdlieu) {
		this.cdlieu = cdlieu;
	}

	public String getPoserv() {
		return poserv;
	}

	public void setPoserv(String poserv) {
		this.poserv = poserv;
	}

	public Integer getCtitre() {
		return ctitre;
	}

	public void setCtitre(Integer ctitre) {
		this.ctitre = ctitre;
	}

	public Integer getPoetud() {
		return poetud;
	}

	public void setPoetud(Integer poetud) {
		this.poetud = poetud;
	}

	public Integer getCrespo() {
		return crespo;
	}

	public void setCrespo(Integer crespo) {
		this.crespo = crespo;
	}

	public String getPograd() {
		return pograd;
	}

	public void setPograd(String pograd) {
		this.pograd = pograd;
	}

	public String getPomis1() {
		return pomis1;
	}

	public void setPomis1(String pomis1) {
		this.pomis1 = pomis1;
	}

	public String getPomis2() {
		return pomis2;
	}

	public void setPomis2(String pomis2) {
		this.pomis2 = pomis2;
	}

	public String getPomis3() {
		return pomis3;
	}

	public void setPomis3(String pomis3) {
		this.pomis3 = pomis3;
	}

	public String getPomis4() {
		return pomis4;
	}

	public void setPomis4(String pomis4) {
		this.pomis4 = pomis4;
	}

	public Integer getPomatr() {
		return pomatr;
	}

	public void setPomatr(Integer pomatr) {
		this.pomatr = pomatr;
	}

	public String getPocond() {
		return pocond;
	}

	public void setPocond(String pocond) {
		this.pocond = pocond;
	}

	public Integer getPodval() {
		return podval;
	}

	public void setPodval(Integer podval) {
		this.podval = podval;
	}

	public Integer getPodsup() {
		return podsup;
	}

	public void setPodsup(Integer podsup) {
		this.podsup = podsup;
	}

	public String getPoserp() {
		return poserp;
	}

	public void setPoserp(String poserp) {
		this.poserp = poserp;
	}

	public String getCodfon() {
		return codfon;
	}

	public void setCodfon(String codfon) {
		this.codfon = codfon;
	}

	public String getCodact() {
		return codact;
	}

	public void setCodact(String codact) {
		this.codact = codact;
	}

	public String getNoacti() {
		return noacti;
	}

	public void setNoacti(String noacti) {
		this.noacti = noacti;
	}

	public Integer getReglem() {
		return reglem;
	}

	public void setReglem(Integer reglem) {
		this.reglem = reglem;
	}

	public String getCommen() {
		return commen;
	}

	public void setCommen(String commen) {
		this.commen = commen;
	}

	public Integer getBudget() {
		return budget;
	}

	public void setBudget(Integer budget) {
		this.budget = budget;
	}

	public Integer getSoanne() {
		return soanne;
	}

	public void setSoanne(Integer soanne) {
		this.soanne = soanne;
	}

	public Integer getSonuor() {
		return sonuor;
	}

	public void setSonuor(Integer sonuor) {
		this.sonuor = sonuor;
	}

	public Integer getPrimaire() {
		return primaire;
	}

	public void setPrimaire(Integer primaire) {
		this.primaire = primaire;
	}

}
