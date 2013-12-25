// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.AvantageNature;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AvantageNature_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager AvantageNature.entityManager;
    
    public static final EntityManager AvantageNature.entityManager() {
        EntityManager em = new AvantageNature().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AvantageNature.countAvantageNatures() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AvantageNature o", Long.class).getSingleResult();
    }
    
    public static List<AvantageNature> AvantageNature.findAllAvantageNatures() {
        return entityManager().createQuery("SELECT o FROM AvantageNature o", AvantageNature.class).getResultList();
    }
    
    public static AvantageNature AvantageNature.findAvantageNature(Integer idAvantage) {
        if (idAvantage == null) return null;
        return entityManager().find(AvantageNature.class, idAvantage);
    }
    
    public static List<AvantageNature> AvantageNature.findAvantageNatureEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AvantageNature o", AvantageNature.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void AvantageNature.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AvantageNature.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AvantageNature attached = AvantageNature.findAvantageNature(this.idAvantage);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AvantageNature.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AvantageNature.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AvantageNature AvantageNature.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AvantageNature merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}