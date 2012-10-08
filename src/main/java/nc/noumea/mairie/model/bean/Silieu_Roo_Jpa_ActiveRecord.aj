// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.Silieu;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Silieu_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager Silieu.entityManager;
    
    public static final EntityManager Silieu.entityManager() {
        EntityManager em = new Silieu().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Silieu.countSilieus() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Silieu o", Long.class).getSingleResult();
    }
    
    public static List<Silieu> Silieu.findAllSilieus() {
        return entityManager().createQuery("SELECT o FROM Silieu o", Silieu.class).getResultList();
    }
    
    public static Silieu Silieu.findSilieu(Long codeLieu) {
        if (codeLieu == null) return null;
        return entityManager().find(Silieu.class, codeLieu);
    }
    
    public static List<Silieu> Silieu.findSilieuEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Silieu o", Silieu.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Silieu.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Silieu.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Silieu attached = Silieu.findSilieu(this.codeLieu);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Silieu.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Silieu.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Silieu Silieu.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Silieu merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
