package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;

import nc.noumea.mairie.model.pk.SiguicId;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", schema = "MAIRIE", table = "SIGUIC", versionField = "")
public class Siguic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SiguicId id;

	@Column(name = "LIGUIC", columnDefinition = "char")
	private String liGuic;

	public Siguic() {
	}

	public Siguic(Integer codeBanque, Integer codeGuichet) {
		this.id = new SiguicId(codeBanque, codeGuichet);
	}
}
