// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import nc.noumea.mairie.model.bean.Contact;
import nc.noumea.mairie.model.bean.TypeContact;

privileged aspect Contact_Roo_JavaBean {
    
    public TypeContact Contact.getTypeContact() {
        return this.typeContact;
    }
    
    public void Contact.setTypeContact(TypeContact typeContact) {
        this.typeContact = typeContact;
    }
    
    public Integer Contact.getIdAgent() {
        return this.idAgent;
    }
    
    public void Contact.setIdAgent(Integer idAgent) {
        this.idAgent = idAgent;
    }
    
    public String Contact.getDescription() {
        return this.description;
    }
    
    public void Contact.setDescription(String description) {
        this.description = description;
    }
    
    public void Contact.setDiffusable(String diffusable) {
        this.diffusable = diffusable;
    }
    
    public Integer Contact.getContactPrioritaire() {
        return this.contactPrioritaire;
    }
    
    public void Contact.setContactPrioritaire(Integer contactPrioritaire) {
        this.contactPrioritaire = contactPrioritaire;
    }
    
    public void Contact.setPrioritaire(String prioritaire) {
        this.prioritaire = prioritaire;
    }
    
}
