// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.Date;
import nc.noumea.mairie.model.bean.Affectation;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.AgentRecherche;
import nc.noumea.mairie.model.bean.FichePoste;

privileged aspect Affectation_Roo_JavaBean {
    
    public Agent Affectation.getAgent() {
        return this.agent;
    }
    
    public void Affectation.setAgent(Agent agent) {
        this.agent = agent;
    }
    
    public AgentRecherche Affectation.getAgentrecherche() {
        return this.agentrecherche;
    }
    
    public void Affectation.setAgentrecherche(AgentRecherche agentrecherche) {
        this.agentrecherche = agentrecherche;
    }
    
    public FichePoste Affectation.getFichePoste() {
        return this.fichePoste;
    }
    
    public void Affectation.setFichePoste(FichePoste fichePoste) {
        this.fichePoste = fichePoste;
    }
    
    public Date Affectation.getDateDebutAff() {
        return this.dateDebutAff;
    }
    
    public void Affectation.setDateDebutAff(Date dateDebutAff) {
        this.dateDebutAff = dateDebutAff;
    }
    
    public Date Affectation.getDateFinAff() {
        return this.dateFinAff;
    }
    
    public void Affectation.setDateFinAff(Date dateFinAff) {
        this.dateFinAff = dateFinAff;
    }
    
    public String Affectation.getTempsTravail() {
        return this.tempsTravail;
    }
    
    public void Affectation.setTempsTravail(String tempsTravail) {
        this.tempsTravail = tempsTravail;
    }
    
    public FichePoste Affectation.getFichePosteSecondaire() {
        return this.fichePosteSecondaire;
    }
    
    public void Affectation.setFichePosteSecondaire(FichePoste fichePosteSecondaire) {
        this.fichePosteSecondaire = fichePosteSecondaire;
    }
    
}
