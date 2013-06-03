// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import nc.noumea.mairie.model.bean.AgentRecherche;
import org.springframework.transaction.annotation.Transactional;

privileged aspect AgentRecherche_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext(unitName = "sirhPersistenceUnit")
    transient EntityManager AgentRecherche.entityManager;
    
    public static final EntityManager AgentRecherche.entityManager() {
        EntityManager em = new AgentRecherche().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long AgentRecherche.countAgentRecherches() {
        return entityManager().createQuery("SELECT COUNT(o) FROM AgentRecherche o", Long.class).getSingleResult();
    }
    
    public static List<AgentRecherche> AgentRecherche.findAllAgentRecherches() {
        return entityManager().createQuery("SELECT o FROM AgentRecherche o", AgentRecherche.class).getResultList();
    }
    
    public static AgentRecherche AgentRecherche.findAgentRecherche(Integer idAgent) {
        if (idAgent == null) return null;
        return entityManager().find(AgentRecherche.class, idAgent);
    }
    
    public static List<AgentRecherche> AgentRecherche.findAgentRechercheEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM AgentRecherche o", AgentRecherche.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void AgentRecherche.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void AgentRecherche.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            AgentRecherche attached = AgentRecherche.findAgentRecherche(this.idAgent);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void AgentRecherche.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void AgentRecherche.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public AgentRecherche AgentRecherche.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        AgentRecherche merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
