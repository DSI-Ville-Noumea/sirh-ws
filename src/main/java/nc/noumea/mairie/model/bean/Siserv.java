package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.service.ISiservService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "SISERV", versionField = "")
public class Siserv {

	@Autowired
	@Transient
	ISiservService siservSrv;

	@Id
	@Column(name = "SERVI", columnDefinition = "char")
	private String servi;

	@NotNull
	@Column(name = "LISERV", columnDefinition = "char")
	private String liServ;

	@NotNull
	@Column(name = "CODACT", columnDefinition = "char")
	private String codeActif;

	@NotNull
	@Column(name = "SIGLE", columnDefinition = "char")
	private String sigle;

	@NotNull
	@Column(name = "DEPEND", columnDefinition = "char")
	private String parentSigle;

	@Transient
	private String division;

	public String getDivision() {
		String service = "";
		if (this.servi != null) {
			Siserv divisionService = siservSrv.getDivision(this.servi);
			if (divisionService != null) {
				service = divisionService.getLiServ().trim();
			} else {
				Siserv servicePoste = siservSrv.getService(this.servi);
				if (servicePoste != null) {
					service = servicePoste.getLiServ().trim();
				}
			}
		}
		return service;
	}

	@Transient
	private String direction;

	public String getDirection() {
		String direction = "";
		if (this.servi != null) {
			Siserv directionService = siservSrv.getDirection(this.servi);
			if (directionService != null) {
				direction = directionService.getLiServ().trim();
			}
		}
		return direction;
	}

	@Transient
	private String section;

	public String getSection() {
		String section = "";
		if (this.servi != null) {
			Siserv sectionService = siservSrv.getSection(this.servi);
			if (sectionService != null) {
				section = sectionService.getLiServ().trim();
			}
		}
		return section;
	}
}
