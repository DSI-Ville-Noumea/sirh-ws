package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.pk.CompetenceFPPK;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = CompetenceFPPK.class, table = "COMPETENCE_FP", schema = "SIRH", versionField = "")
public class CompetenceFP {
}
