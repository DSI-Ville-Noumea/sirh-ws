// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import nc.noumea.mairie.model.bean.Budget;

privileged aspect Budget_Roo_Jpa_Entity {
    
    declare @type: Budget: @Entity;
    
    declare @type: Budget: @Table(name = "R_BUDGET");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_BUDGET")
    private Integer Budget.idBudget;
    
    public Integer Budget.getIdBudget() {
        return this.idBudget;
    }
    
    public void Budget.setIdBudget(Integer id) {
        this.idBudget = id;
    }
    
}
