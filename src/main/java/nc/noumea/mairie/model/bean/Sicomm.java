package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(schema = "MAIRIE", identifierColumn = "CODCOM", identifierField = "codeCommune", table = "SICOMM")
public class Sicomm {

    @NotNull
    @Column(name = "LIBVIL")
    private String libVil;
}
