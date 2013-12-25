// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.PrimePointageFP;
import nc.noumea.mairie.model.pk.PrimePointageFPPK;
import org.springframework.transaction.annotation.Transactional;

privileged aspect PrimePointageFP_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager PrimePointageFP.entityManager;
    
    public static final EntityManager PrimePointageFP.entityManager() {
        EntityManager em = new PrimePointageFP().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long PrimePointageFP.countPrimePointageFPs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM PrimePointageFP o", Long.class).getSingleResult();
    }
    
    public static List<PrimePointageFP> PrimePointageFP.findAllPrimePointageFPs() {
        return entityManager().createQuery("SELECT o FROM PrimePointageFP o", PrimePointageFP.class).getResultList();
    }
    
    public static PrimePointageFP PrimePointageFP.findPrimePointageFP(PrimePointageFPPK id) {
        if (id == null) return null;
        return entityManager().find(PrimePointageFP.class, id);
    }
    
    public static List<PrimePointageFP> PrimePointageFP.findPrimePointageFPEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM PrimePointageFP o", PrimePointageFP.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void PrimePointageFP.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void PrimePointageFP.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            PrimePointageFP attached = PrimePointageFP.findPrimePointageFP(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void PrimePointageFP.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void PrimePointageFP.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public PrimePointageFP PrimePointageFP.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        PrimePointageFP merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}