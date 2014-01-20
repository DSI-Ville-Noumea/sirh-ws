// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean.eae;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.eae.EaeFinalisation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect EaeFinalisation_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "eaePersistenceUnit")
    transient EntityManager EaeFinalisation.entityManager;
    
    public static final List<String> EaeFinalisation.fieldNames4OrderClauseFilter = java.util.Arrays.asList("dateFinalisation", "idAgent", "idGedDocument", "versionGedDocument", "commentaire", "eae");
    
    public static final EntityManager EaeFinalisation.entityManager() {
        EntityManager em = new EaeFinalisation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long EaeFinalisation.countEaeFinalisations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM EaeFinalisation o", Long.class).getSingleResult();
    }
    
    public static List<EaeFinalisation> EaeFinalisation.findAllEaeFinalisations() {
        return entityManager().createQuery("SELECT o FROM EaeFinalisation o", EaeFinalisation.class).getResultList();
    }
    
    public static List<EaeFinalisation> EaeFinalisation.findAllEaeFinalisations(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM EaeFinalisation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, EaeFinalisation.class).getResultList();
    }
    
    public static EaeFinalisation EaeFinalisation.findEaeFinalisation(Integer idEaeFinalisation) {
        if (idEaeFinalisation == null) return null;
        return entityManager().find(EaeFinalisation.class, idEaeFinalisation);
    }
    
    public static List<EaeFinalisation> EaeFinalisation.findEaeFinalisationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM EaeFinalisation o", EaeFinalisation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<EaeFinalisation> EaeFinalisation.findEaeFinalisationEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM EaeFinalisation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, EaeFinalisation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void EaeFinalisation.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void EaeFinalisation.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            EaeFinalisation attached = EaeFinalisation.findEaeFinalisation(this.idEaeFinalisation);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void EaeFinalisation.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void EaeFinalisation.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public EaeFinalisation EaeFinalisation.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        EaeFinalisation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
