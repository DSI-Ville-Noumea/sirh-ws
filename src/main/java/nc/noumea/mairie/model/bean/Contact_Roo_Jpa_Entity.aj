// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import nc.noumea.mairie.model.bean.Contact;

privileged aspect Contact_Roo_Jpa_Entity {
    
    declare @type: Contact: @Entity;
    
    declare @type: Contact: @Table(name = "CONTACT");
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_CONTACT")
    private Integer Contact.idContact;
    
    public Integer Contact.getIdContact() {
        return this.idContact;
    }
    
    public void Contact.setIdContact(Integer id) {
        this.idContact = id;
    }
    
}
