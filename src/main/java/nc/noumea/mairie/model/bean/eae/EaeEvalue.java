package nc.noumea.mairie.model.bean.eae;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(persistenceUnit = "eaePersistenceUnit", identifierColumn = "ID_EAE_EVALUE", identifierField = "idEaeEvalue", identifierType = Integer.class, table = "EAE_EVALUE", sequenceName="EAE_S_EVALUE")
public class EaeEvalue {

    @Column(name = "ID_AGENT")
    private int idAgent;

    @OneToOne
    @JoinColumn(name = "ID_EAE")
    private Eae eae;

}
