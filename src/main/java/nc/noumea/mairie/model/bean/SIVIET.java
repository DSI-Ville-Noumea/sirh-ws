package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nc.noumea.mairie.model.pk.SivietId;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SIVIET", versionField = "")
public class SIVIET implements Serializable {

	@EmbeddedId
	private SivietId id;

	@Column(name = "LIBCOP", columnDefinition = "char")
	private String libCop;

	public SIVIET() {
	}

	public SIVIET(Integer codePays, Integer sousCodePays) {
		this.id = new SivietId(codePays, sousCodePays);
	}
}
