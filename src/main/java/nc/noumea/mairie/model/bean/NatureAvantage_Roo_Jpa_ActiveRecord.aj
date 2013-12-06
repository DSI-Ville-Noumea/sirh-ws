// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.NatureAvantage;
import org.springframework.transaction.annotation.Transactional;

privileged aspect NatureAvantage_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager NatureAvantage.entityManager;
    
    public static final EntityManager NatureAvantage.entityManager() {
        EntityManager em = new NatureAvantage().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long NatureAvantage.countNatureAvantages() {
        return entityManager().createQuery("SELECT COUNT(o) FROM NatureAvantage o", Long.class).getSingleResult();
    }
    
    public static List<NatureAvantage> NatureAvantage.findAllNatureAvantages() {
        return entityManager().createQuery("SELECT o FROM NatureAvantage o", NatureAvantage.class).getResultList();
    }
    
    public static NatureAvantage NatureAvantage.findNatureAvantage(Integer idNatureAvantage) {
        if (idNatureAvantage == null) return null;
        return entityManager().find(NatureAvantage.class, idNatureAvantage);
    }
    
    public static List<NatureAvantage> NatureAvantage.findNatureAvantageEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM NatureAvantage o", NatureAvantage.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void NatureAvantage.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void NatureAvantage.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            NatureAvantage attached = NatureAvantage.findNatureAvantage(this.idNatureAvantage);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void NatureAvantage.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void NatureAvantage.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public NatureAvantage NatureAvantage.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        NatureAvantage merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
