package nc.noumea.mairie.model.bean.sirh;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "P_ALERTE_KIOSQUE")
@PersistenceUnit(unitName = "sirhPersistenceUnit")
public class AlerteRh {

	@Id
	@Column(name = "ID_ALERTE_KIOSQUE")
	private Integer idAlerteKiosque;

	@Column(name = "TEXTE_ALERTE_KIOSQUE")
	@Lob
	private String texteAlerteKiosque;

	@Column(name = "AGENT")
	private boolean agent;

	@Column(name = "APPRO_ABS")
	private boolean approbateurABS;

	@Column(name = "APPRO_PTG")
	private boolean approbateurPTG;

	@Column(name = "OPE_ABS")
	private boolean operateurABS;

	@Column(name = "OPE_PTG")
	private boolean operateurPTG;

	@Column(name = "VISEUR_ABS")
	private boolean viseurABS;

	@Column(name = "DATE_DEBUT")
	@Temporal(TemporalType.DATE)
	private Date dateDebut;

	@Column(name = "DATE_FIN")
	@Temporal(TemporalType.DATE)
	private Date dateFin;

	public Integer getIdAlerteKiosque() {
		return idAlerteKiosque;
	}

	public void setIdAlerteKiosque(Integer idAlerteKiosque) {
		this.idAlerteKiosque = idAlerteKiosque;
	}

	public String getTexteAlerteKiosque() {
		return texteAlerteKiosque;
	}

	public void setTexteAlerteKiosque(String texteAlerteKiosque) {
		this.texteAlerteKiosque = texteAlerteKiosque;
	}

	public boolean isAgent() {
		return agent;
	}

	public void setAgent(boolean agent) {
		this.agent = agent;
	}

	public boolean isApprobateurABS() {
		return approbateurABS;
	}

	public void setApprobateurABS(boolean approbateurABS) {
		this.approbateurABS = approbateurABS;
	}

	public boolean isApprobateurPTG() {
		return approbateurPTG;
	}

	public void setApprobateurPTG(boolean approbateurPTG) {
		this.approbateurPTG = approbateurPTG;
	}

	public boolean isOperateurABS() {
		return operateurABS;
	}

	public void setOperateurABS(boolean operateurABS) {
		this.operateurABS = operateurABS;
	}

	public boolean isOperateurPTG() {
		return operateurPTG;
	}

	public void setOperateurPTG(boolean operateurPTG) {
		this.operateurPTG = operateurPTG;
	}

	public boolean isViseurABS() {
		return viseurABS;
	}

	public void setViseurABS(boolean viseurABS) {
		this.viseurABS = viseurABS;
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
}
