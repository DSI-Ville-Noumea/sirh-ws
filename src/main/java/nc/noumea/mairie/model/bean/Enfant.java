package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierColumn = "ID_ENFANT", schema = "SIRH", identifierField = "idEnfant", identifierType = Integer.class, table = "ENFANT")
@RooSerializable
@RooJson
public class Enfant {

    @NotNull
    @Column(name = "NOM")
    private String nom;
}
