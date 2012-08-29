// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.TitrePoste;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TitrePoste_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager TitrePoste.entityManager;
    
    public static final EntityManager TitrePoste.entityManager() {
        EntityManager em = new TitrePoste().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TitrePoste.countTitrePostes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TitrePoste o", Long.class).getSingleResult();
    }
    
    public static List<TitrePoste> TitrePoste.findAllTitrePostes() {
        return entityManager().createQuery("SELECT o FROM TitrePoste o", TitrePoste.class).getResultList();
    }
    
    public static TitrePoste TitrePoste.findTitrePoste(Long idTitrePoste) {
        if (idTitrePoste == null) return null;
        return entityManager().find(TitrePoste.class, idTitrePoste);
    }
    
    public static List<TitrePoste> TitrePoste.findTitrePosteEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TitrePoste o", TitrePoste.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void TitrePoste.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TitrePoste.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TitrePoste attached = TitrePoste.findTitrePoste(this.idTitrePoste);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TitrePoste.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TitrePoste.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public TitrePoste TitrePoste.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TitrePoste merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
