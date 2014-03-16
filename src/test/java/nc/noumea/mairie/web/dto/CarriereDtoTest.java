package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spcatg;
import nc.noumea.mairie.model.bean.Spgradn;
import nc.noumea.mairie.model.pk.SpcarrId;

import org.joda.time.DateTime;
import org.junit.Test;

public class CarriereDtoTest extends AgentDto {

	@Test
	public void testCalculEaeInfosDto_cst() {
		// Given
		Spgradn grade = new Spgradn();
		grade.setCdgrad("CDGRAD");
		Spcatg categorie = new Spcatg();
		categorie.setCodeCategorie(1);
		categorie.setLibelleCategorie("LIB CAT");
		SpcarrId id = new SpcarrId();
		id.setNomatr(5138);
		id.setDatdeb(20140101);
		Spcarr carr = new Spcarr();
		carr.setCategorie(categorie);
		carr.setId(id);
		carr.setGrade(grade);

		// When
		CarriereDto dto = new CarriereDto(carr);

		// Then
		assertEquals(carr.getCategorie().getCodeCategorie(), dto.getCodeCategorie());
		assertEquals(new DateTime(2014, 01, 01, 0, 0, 0).toDate(), dto.getDateDebut());
		assertEquals(carr.getGrade().getCdgrad(), dto.getGrade().getCodeGrade());
		assertEquals(carr.getCategorie().getLibelleCategorie(), dto.getLibelleCategorie());
		assertEquals(carr.getId().getNomatr(), dto.getNoMatr());

	}
}
