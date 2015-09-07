package nc.noumea.mairie.service.eae;

import java.util.Comparator;

import nc.noumea.mairie.web.dto.ParcoursProDto;

public class EaeParcoursProByDateDebutInverseComparator implements Comparator<ParcoursProDto> {

	@Override
	public int compare(ParcoursProDto o1, ParcoursProDto o2) {
		return o2.getDateDebut().compareTo(o1.getDateDebut());
	}
}