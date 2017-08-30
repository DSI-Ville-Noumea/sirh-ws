package nc.noumea.mairie.mdf.domain.cde.pers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PersistenceUnit;
import javax.persistence.Table;

@Entity
@Table(name = "FDSMUTTOT2")
@PersistenceUnit(unitName = "mdfCdePersistenceUnit")
public class FdsMutTotPers {
	
	@Id
	@Column(name = "DATFIC2", columnDefinition = "char")
	private String dateFichier;

	@Column(name = "TYPEN3", columnDefinition = "numeric")
	private Integer typeEnregistrement;
	
	@Column(name = "NBLIGD", columnDefinition = "numeric")
	private Integer nombreLignesDetail;
	
	@Column(name = "COLLEC3", columnDefinition = "char")
	private String codeCollectivité;

	public Integer getTypeEnregistrement() {
		return typeEnregistrement;
	}

	public void setTypeEnregistrement(Integer typeEnregistrement) {
		this.typeEnregistrement = typeEnregistrement;
	}

	public Integer getNombreLignesDetail() {
		return nombreLignesDetail;
	}

	public void setNombreLignesDetail(Integer nombreLignesDetail) {
		this.nombreLignesDetail = nombreLignesDetail;
	}

	public String getCodeCollectivité() {
		return codeCollectivité;
	}

	public void setCodeCollectivité(String codeCollectivité) {
		this.codeCollectivité = codeCollectivité;
	}

	public String getDateFichier() {
		return dateFichier;
	}

	public void setDateFichier(String dateFichier) {
		this.dateFichier = dateFichier;
	}

}
