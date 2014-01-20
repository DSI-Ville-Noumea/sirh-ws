// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.Deliberation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Deliberation_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager Deliberation.entityManager;
    
    public static final List<String> Deliberation.fieldNames4OrderClauseFilter = java.util.Arrays.asList("libDeliberation", "texteCap");
    
    public static final EntityManager Deliberation.entityManager() {
        EntityManager em = new Deliberation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Deliberation.countDeliberations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Deliberation o", Long.class).getSingleResult();
    }
    
    public static List<Deliberation> Deliberation.findAllDeliberations() {
        return entityManager().createQuery("SELECT o FROM Deliberation o", Deliberation.class).getResultList();
    }
    
    public static List<Deliberation> Deliberation.findAllDeliberations(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Deliberation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Deliberation.class).getResultList();
    }
    
    public static Deliberation Deliberation.findDeliberation(Integer idDeliberation) {
        if (idDeliberation == null) return null;
        return entityManager().find(Deliberation.class, idDeliberation);
    }
    
    public static List<Deliberation> Deliberation.findDeliberationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Deliberation o", Deliberation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Deliberation> Deliberation.findDeliberationEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Deliberation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Deliberation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Deliberation.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Deliberation.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Deliberation attached = Deliberation.findDeliberation(this.idDeliberation);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Deliberation.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Deliberation.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Deliberation Deliberation.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Deliberation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
