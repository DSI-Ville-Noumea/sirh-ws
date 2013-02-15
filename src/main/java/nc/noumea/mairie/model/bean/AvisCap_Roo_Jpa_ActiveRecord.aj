// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.AvisCap;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AvisCap_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager AvisCap.entityManager;
    
    public static final EntityManager AvisCap.entityManager() {
        EntityManager em = new AvisCap().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AvisCap.countAvisCaps() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AvisCap o", Long.class).getSingleResult();
    }
    
    public static List<AvisCap> AvisCap.findAllAvisCaps() {
        return entityManager().createQuery("SELECT o FROM AvisCap o", AvisCap.class).getResultList();
    }
    
    public static AvisCap AvisCap.findAvisCap(Integer idAvisCap) {
        if (idAvisCap == null) return null;
        return entityManager().find(AvisCap.class, idAvisCap);
    }
    
    public static List<AvisCap> AvisCap.findAvisCapEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AvisCap o", AvisCap.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void AvisCap.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AvisCap.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AvisCap attached = AvisCap.findAvisCap(this.idAvisCap);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AvisCap.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AvisCap.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AvisCap AvisCap.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AvisCap merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
