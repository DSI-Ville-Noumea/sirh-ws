// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package nc.noumea.mairie.model.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

privileged aspect TitrePoste_Roo_Jpa_Entity {

	declare @type: TitrePoste: @Entity;

	declare @type: TitrePoste: @Table(schema = "SIRH", name = "P_TITRE_POSTE");

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_TITRE_POSTE")
	private Integer TitrePoste.idTitrePoste;

	public Integer TitrePoste.getIdTitrePoste() {
		return this.idTitrePoste;
	}

	public void TitrePoste.setIdTitrePoste(Integer id) {
		this.idTitrePoste = id;
	}

}
