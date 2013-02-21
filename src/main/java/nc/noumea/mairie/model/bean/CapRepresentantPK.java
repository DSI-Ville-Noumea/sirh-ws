package nc.noumea.mairie.model.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.springframework.roo.addon.equals.RooEquals;

@Embeddable
@RooEquals
public class CapRepresentantPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ID_CAP")
	private Integer idCap;
	
	@Column(name = "ID_REPRESENTANT")
	private Integer idRepresentant;
}
