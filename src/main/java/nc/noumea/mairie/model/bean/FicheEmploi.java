package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierType = Integer.class, identifierColumn = "ID_FICHE_EMPLOI", identifierField = "idFicheEmploi", table = "FICHE_EMPLOI", versionField = "")
public class FicheEmploi {
	
	@Column(name = "REF_MAIRIE")
	private String refMairie;
}
