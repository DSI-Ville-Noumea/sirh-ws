package nc.noumea.mairie.model.bean;

import javax.persistence.Column;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJson
@RooJpaActiveRecord(persistenceUnit = "sirhPersistenceUnit", identifierType = Integer.class, identifierColumn = "ID_TITRE_POSTE", identifierField = "idTitrePoste", schema = "SIRH", table = "P_TITRE_POSTE", versionField = "")
public class TitrePoste {

	@Column(name = "LIB_TITRE_POSTE")
	private String libTitrePoste;
}
