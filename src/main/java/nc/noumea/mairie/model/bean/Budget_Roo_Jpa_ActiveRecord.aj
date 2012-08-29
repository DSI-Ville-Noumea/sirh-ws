// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.Budget;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Budget_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Budget.entityManager;
    
    public static final EntityManager Budget.entityManager() {
        EntityManager em = new Budget().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Budget.countBudgets() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Budget o", Long.class).getSingleResult();
    }
    
    public static List<Budget> Budget.findAllBudgets() {
        return entityManager().createQuery("SELECT o FROM Budget o", Budget.class).getResultList();
    }
    
    public static Budget Budget.findBudget(Long idBudget) {
        if (idBudget == null) return null;
        return entityManager().find(Budget.class, idBudget);
    }
    
    public static List<Budget> Budget.findBudgetEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Budget o", Budget.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Budget.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Budget.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Budget attached = Budget.findBudget(this.idBudget);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Budget.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Budget.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Budget Budget.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Budget merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
