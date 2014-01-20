// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.Diplome;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Diplome_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager Diplome.entityManager;
    
    public static final List<String> Diplome.fieldNames4OrderClauseFilter = java.util.Arrays.asList("serialVersionUID", "libDiplomen");
    
    public static final EntityManager Diplome.entityManager() {
        EntityManager em = new Diplome().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Diplome.countDiplomes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Diplome o", Long.class).getSingleResult();
    }
    
    public static List<Diplome> Diplome.findAllDiplomes() {
        return entityManager().createQuery("SELECT o FROM Diplome o", Diplome.class).getResultList();
    }
    
    public static List<Diplome> Diplome.findAllDiplomes(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Diplome o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Diplome.class).getResultList();
    }
    
    public static Diplome Diplome.findDiplome(Integer idDiplomeGenerique) {
        if (idDiplomeGenerique == null) return null;
        return entityManager().find(Diplome.class, idDiplomeGenerique);
    }
    
    public static List<Diplome> Diplome.findDiplomeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Diplome o", Diplome.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<Diplome> Diplome.findDiplomeEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM Diplome o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, Diplome.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Diplome.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Diplome.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Diplome attached = Diplome.findDiplome(this.idDiplomeGenerique);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Diplome.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Diplome.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Diplome Diplome.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Diplome merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
