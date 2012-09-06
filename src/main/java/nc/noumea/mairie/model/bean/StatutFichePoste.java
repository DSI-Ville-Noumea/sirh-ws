package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = Integer.class, identifierColumn = "ID_STATUT_FP", identifierField = "idStatutFp", schema = "SIRH", table = "R_STATUT_FP")
public class StatutFichePoste {
	
	@NotNull
	@Column(name = "LIB_STATUT_FP")
	private String libStatut;
}
