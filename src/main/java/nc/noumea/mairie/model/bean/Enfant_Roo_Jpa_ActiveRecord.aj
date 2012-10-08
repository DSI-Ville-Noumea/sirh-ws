// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.Enfant;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Enfant_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager Enfant.entityManager;
    
    public static final EntityManager Enfant.entityManager() {
        EntityManager em = new Enfant().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Enfant.countEnfants() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Enfant o", Long.class).getSingleResult();
    }
    
    public static List<Enfant> Enfant.findAllEnfants() {
        return entityManager().createQuery("SELECT o FROM Enfant o", Enfant.class).getResultList();
    }
    
    public static Enfant Enfant.findEnfant(Integer idEnfant) {
        if (idEnfant == null) return null;
        return entityManager().find(Enfant.class, idEnfant);
    }
    
    public static List<Enfant> Enfant.findEnfantEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Enfant o", Enfant.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Enfant.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Enfant.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Enfant attached = Enfant.findEnfant(this.idEnfant);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Enfant.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Enfant.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Enfant Enfant.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Enfant merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
