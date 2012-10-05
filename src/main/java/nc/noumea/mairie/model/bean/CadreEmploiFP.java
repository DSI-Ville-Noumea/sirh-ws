package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.pk.CadreEmploiFPPK;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = CadreEmploiFPPK.class, table = "CADRE_EMPLOI_FP", schema = "SIRH", versionField = "")
public class CadreEmploiFP {
}
