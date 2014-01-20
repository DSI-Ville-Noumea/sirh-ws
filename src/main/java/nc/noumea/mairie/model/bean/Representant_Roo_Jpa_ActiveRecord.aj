// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.Representant;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Representant_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager Representant.entityManager;
    
    public static final List<String> Representant.fieldNames4OrderClauseFilter = java.util.Arrays.asList("nom", "prenom");
    
    public static final EntityManager Representant.entityManager() {
        EntityManager em = new Representant().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Representant.countRepresentants() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Representant o", Long.class).getSingleResult();
    }
    
    public static List<Representant> Representant.findAllRepresentants() {
        return entityManager().createQuery("SELECT o FROM Representant o", Representant.class).getResultList();
    }
    
    public static List<Representant> Representant.findAllRepresentants(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Representant o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Representant.class).getResultList();
    }
    
    public static Representant Representant.findRepresentant(Integer idRepresentant) {
        if (idRepresentant == null) return null;
        return entityManager().find(Representant.class, idRepresentant);
    }
    
    public static List<Representant> Representant.findRepresentantEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Representant o", Representant.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Representant> Representant.findRepresentantEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Representant o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Representant.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Representant.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Representant.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Representant attached = Representant.findRepresentant(this.idRepresentant);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Representant.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Representant.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Representant Representant.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Representant merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
