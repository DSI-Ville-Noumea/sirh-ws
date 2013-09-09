package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import nc.noumea.mairie.model.pk.SpcarrId;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", table = "SPCARR", versionField = "")
@NamedQuery(name = "getCurrentCarriere", query = "select carr from Spcarr carr where carr.id.nomatr = :nomatr and carr.id.datdeb <= :todayFormatMairie and (carr.dateFin = 0 or carr.dateFin >= :todayFormatMairie)")
public class Spcarr {

	@EmbeddedId
	private SpcarrId id;

	public Spcarr() {
	}

	public Spcarr(Integer nomatr, Integer datdeb) {
		this.id = new SpcarrId(nomatr, datdeb);
	}

	@NotNull
	@Column(name = "DATFIN", columnDefinition = "numeric")
	private Integer dateFin;

	@NotNull
	@Column(name = "DATARR", columnDefinition = "numeric")
	private Integer dateArrete;

	@NotNull
	@Column(name = "REFARR", columnDefinition = "numeric")
	private Integer referenceArrete;
}
