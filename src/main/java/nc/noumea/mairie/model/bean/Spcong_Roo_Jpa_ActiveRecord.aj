// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.Spcong;
import nc.noumea.mairie.model.pk.SpcongId;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Spcong_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager Spcong.entityManager;
    
    public static final EntityManager Spcong.entityManager() {
        EntityManager em = new Spcong().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Spcong.countSpcongs() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Spcong o", Long.class).getSingleResult();
    }
    
    public static List<Spcong> Spcong.findAllSpcongs() {
        return entityManager().createQuery("SELECT o FROM Spcong o", Spcong.class).getResultList();
    }
    
    public static Spcong Spcong.findSpcong(SpcongId id) {
        if (id == null) return null;
        return entityManager().find(Spcong.class, id);
    }
    
    public static List<Spcong> Spcong.findSpcongEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Spcong o", Spcong.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Spcong.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Spcong.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Spcong attached = Spcong.findSpcong(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Spcong.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Spcong.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Spcong Spcong.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Spcong merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
