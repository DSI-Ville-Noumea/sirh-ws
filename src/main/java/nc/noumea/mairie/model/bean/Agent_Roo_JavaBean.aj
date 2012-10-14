// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import java.util.Date;
import java.util.Set;
import nc.noumea.mairie.model.bean.Agent;
import nc.noumea.mairie.model.bean.Enfant;
import nc.noumea.mairie.model.bean.Sicomm;
import nc.noumea.mairie.model.bean.SituationFamiliale;
import nc.noumea.mairie.model.bean.Sivoie;

privileged aspect Agent_Roo_JavaBean {
    
    public Set<Enfant> Agent.getEnfants() {
        return this.enfants;
    }
    
    public void Agent.setEnfants(Set<Enfant> enfants) {
        this.enfants = enfants;
    }
    
    public Integer Agent.getNomatr() {
        return this.nomatr;
    }
    
    public void Agent.setNomatr(Integer nomatr) {
        this.nomatr = nomatr;
    }
    
    public String Agent.getNomPatronymique() {
        return this.nomPatronymique;
    }
    
    public void Agent.setNomPatronymique(String nomPatronymique) {
        this.nomPatronymique = nomPatronymique;
    }
    
    public String Agent.getNomMarital() {
        return this.nomMarital;
    }
    
    public void Agent.setNomMarital(String nomMarital) {
        this.nomMarital = nomMarital;
    }
    
    public String Agent.getNomUsage() {
        return this.nomUsage;
    }
    
    public void Agent.setNomUsage(String nomUsage) {
        this.nomUsage = nomUsage;
    }
    
    public String Agent.getPrenomUsage() {
        return this.prenomUsage;
    }
    
    public void Agent.setPrenomUsage(String prenomUsage) {
        this.prenomUsage = prenomUsage;
    }
    
    public String Agent.getPrenom() {
        return this.prenom;
    }
    
    public void Agent.setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public String Agent.getCivilite() {
        return this.civilite;
    }
    
    public void Agent.setCivilite(String civilite) {
        this.civilite = civilite;
    }
    
    public String Agent.getSexe() {
        return this.sexe;
    }
    
    public void Agent.setSexe(String sexe) {
        this.sexe = sexe;
    }
    
    public Date Agent.getDateNaissance() {
        return this.dateNaissance;
    }
    
    public void Agent.setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }
    
    public SituationFamiliale Agent.getSituationFamiliale() {
        return this.situationFamiliale;
    }
    
    public void Agent.setSituationFamiliale(SituationFamiliale situationFamiliale) {
        this.situationFamiliale = situationFamiliale;
    }
    
    public void Agent.setNumCafat(String numCafat) {
        this.numCafat = numCafat;
    }
    
    public void Agent.setNumRuamm(String numRuamm) {
        this.numRuamm = numRuamm;
    }
    
    public void Agent.setNumMutuelle(String numMutuelle) {
        this.numMutuelle = numMutuelle;
    }
    
    public void Agent.setNumCre(String numCre) {
        this.numCre = numCre;
    }
    
    public void Agent.setNumIrcafex(String numIrcafex) {
        this.numIrcafex = numIrcafex;
    }
    
    public void Agent.setNumClr(String numClr) {
        this.numClr = numClr;
    }
    
    public Sicomm Agent.getCodeCommuneNaissFr() {
        return this.codeCommuneNaissFr;
    }
    
    public void Agent.setCodeCommuneNaissFr(Sicomm codeCommuneNaissFr) {
        this.codeCommuneNaissFr = codeCommuneNaissFr;
    }
    
    public Integer Agent.getCodeCommuneNaissEt() {
        return this.codeCommuneNaissEt;
    }
    
    public void Agent.setCodeCommuneNaissEt(Integer codeCommuneNaissEt) {
        this.codeCommuneNaissEt = codeCommuneNaissEt;
    }
    
    public Integer Agent.getCodePaysNaissEt() {
        return this.codePaysNaissEt;
    }
    
    public void Agent.setCodePaysNaissEt(Integer codePaysNaissEt) {
        this.codePaysNaissEt = codePaysNaissEt;
    }
    
    public void Agent.setIntituleCompte(String intituleCompte) {
        this.intituleCompte = intituleCompte;
    }
    
    public Integer Agent.getRib() {
        return this.rib;
    }
    
    public void Agent.setRib(Integer rib) {
        this.rib = rib;
    }
    
    public String Agent.getNumCompte() {
        return this.numCompte;
    }
    
    public void Agent.setNumCompte(String numCompte) {
        this.numCompte = numCompte;
    }
    
    public Integer Agent.getCodeBanque() {
        return this.codeBanque;
    }
    
    public void Agent.setCodeBanque(Integer codeBanque) {
        this.codeBanque = codeBanque;
    }
    
    public Integer Agent.getCodeGuichet() {
        return this.codeGuichet;
    }
    
    public void Agent.setCodeGuichet(Integer codeGuichet) {
        this.codeGuichet = codeGuichet;
    }
    
    public void Agent.setLieuNaissance(String lieuNaissance) {
        this.lieuNaissance = lieuNaissance;
    }
    
    public void Agent.setBanque(String banque) {
        this.banque = banque;
    }
    
    public Sivoie Agent.getVoie() {
        return this.voie;
    }
    
    public void Agent.setVoie(Sivoie voie) {
        this.voie = voie;
    }
    
    public String Agent.getRueNonNoumea() {
        return this.rueNonNoumea;
    }
    
    public void Agent.setRueNonNoumea(String rueNonNoumea) {
        this.rueNonNoumea = rueNonNoumea;
    }
    
    public void Agent.setBP(String bP) {
        this.bP = bP;
    }
    
    public void Agent.setAdresseComplementaire(String adresseComplementaire) {
        this.adresseComplementaire = adresseComplementaire;
    }
    
    public void Agent.setNumRue(String numRue) {
        this.numRue = numRue;
    }
    
    public void Agent.setBisTer(String bisTer) {
        this.bisTer = bisTer;
    }
    
    public Sicomm Agent.getCodeCommuneVilleDom() {
        return this.codeCommuneVilleDom;
    }
    
    public void Agent.setCodeCommuneVilleDom(Sicomm codeCommuneVilleDom) {
        this.codeCommuneVilleDom = codeCommuneVilleDom;
    }
    
    public Sicomm Agent.getCodeCommuneVilleBP() {
        return this.codeCommuneVilleBP;
    }
    
    public void Agent.setCodeCommuneVilleBP(Sicomm codeCommuneVilleBP) {
        this.codeCommuneVilleBP = codeCommuneVilleBP;
    }
    
    public Integer Agent.getCodePostalVilleDom() {
        return this.codePostalVilleDom;
    }
    
    public void Agent.setCodePostalVilleDom(Integer codePostalVilleDom) {
        this.codePostalVilleDom = codePostalVilleDom;
    }
    
    public Integer Agent.getCodePostalVilleBP() {
        return this.codePostalVilleBP;
    }
    
    public void Agent.setCodePostalVilleBP(Integer codePostalVilleBP) {
        this.codePostalVilleBP = codePostalVilleBP;
    }
    
}
