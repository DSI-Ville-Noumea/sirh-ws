// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.TypeDelegation;
import org.springframework.transaction.annotation.Transactional;

privileged aspect TypeDelegation_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager TypeDelegation.entityManager;
    
    public static final List<String> TypeDelegation.fieldNames4OrderClauseFilter = java.util.Arrays.asList("serialVersionUID", "libTypeDelegation");
    
    public static final EntityManager TypeDelegation.entityManager() {
        EntityManager em = new TypeDelegation().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long TypeDelegation.countTypeDelegations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM TypeDelegation o", Long.class).getSingleResult();
    }
    
    public static List<TypeDelegation> TypeDelegation.findAllTypeDelegations() {
        return entityManager().createQuery("SELECT o FROM TypeDelegation o", TypeDelegation.class).getResultList();
    }
    
    public static List<TypeDelegation> TypeDelegation.findAllTypeDelegations(String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM TypeDelegation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, TypeDelegation.class).getResultList();
    }
    
    public static TypeDelegation TypeDelegation.findTypeDelegation(Integer idTypeDelegation) {
        if (idTypeDelegation == null) return null;
        return entityManager().find(TypeDelegation.class, idTypeDelegation);
    }
    
    public static List<TypeDelegation> TypeDelegation.findTypeDelegationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM TypeDelegation o", TypeDelegation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    public static List<TypeDelegation> TypeDelegation.findTypeDelegationEntries(int firstResult, int maxResults, String sortFieldName, String sortOrder) {
        String jpaQuery = "SELECT o FROM TypeDelegation o";
        if (fieldNames4OrderClauseFilter.contains(sortFieldName)) {
            jpaQuery = jpaQuery + " ORDER BY " + sortFieldName;
            if ("ASC".equalsIgnoreCase(sortOrder) || "DESC".equalsIgnoreCase(sortOrder)) {
                jpaQuery = jpaQuery + " " + sortOrder;
            }
        }
        return entityManager().createQuery(jpaQuery, TypeDelegation.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void TypeDelegation.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void TypeDelegation.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            TypeDelegation attached = TypeDelegation.findTypeDelegation(this.idTypeDelegation);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void TypeDelegation.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void TypeDelegation.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public TypeDelegation TypeDelegation.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        TypeDelegation merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
