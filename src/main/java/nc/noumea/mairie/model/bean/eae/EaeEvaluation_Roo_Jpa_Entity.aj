// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean.eae;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import nc.noumea.mairie.model.bean.eae.EaeEvaluation;

privileged aspect EaeEvaluation_Roo_Jpa_Entity {
    
    declare @type: EaeEvaluation: @Entity;
    
    declare @type: EaeEvaluation: @Table(name = "EAE_EVALUATION");
    
    @Id
    @SequenceGenerator(name = "eaeEvaluationGen", sequenceName = "EAE_S_EVALUATION")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "eaeEvaluationGen")
    @Column(name = "ID_EAE_EVALUATION")
    private Integer EaeEvaluation.idEaeEvaluation;
    
    @Version
    @Column(name = "version")
    private Integer EaeEvaluation.version;
    
    public Integer EaeEvaluation.getIdEaeEvaluation() {
        return this.idEaeEvaluation;
    }
    
    public void EaeEvaluation.setIdEaeEvaluation(Integer id) {
        this.idEaeEvaluation = id;
    }
    
    public Integer EaeEvaluation.getVersion() {
        return this.version;
    }
    
    public void EaeEvaluation.setVersion(Integer version) {
        this.version = version;
    }
    
}