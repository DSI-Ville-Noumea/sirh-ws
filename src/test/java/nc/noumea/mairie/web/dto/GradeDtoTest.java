package nc.noumea.mairie.web.dto;

import static org.junit.Assert.assertEquals;
import nc.noumea.mairie.model.bean.PMotifAvct;
import nc.noumea.mairie.model.bean.Spclas;
import nc.noumea.mairie.model.bean.Speche;
import nc.noumea.mairie.model.bean.Spgeng;
import nc.noumea.mairie.model.bean.Spgradn;

import org.junit.Test;

public class GradeDtoTest {

	@Test
	public void testGradeDto_ctor() {
		// Given
		Spclas spclas = new Spclas();
		spclas.setCodcla("classe");
		spclas.setLibCla("lib classe");
		Speche speche = new Speche();
		speche.setCodEch("classe");
		speche.setLibEch("lib classe");
		Spgeng spgeng = new Spgeng();
		spgeng.setCdgeng("CDCADR");
		Spgradn spgradn = new Spgradn();
		spgradn.setClasse(spclas);
		spgradn.setEchelon(speche);
		spgradn.setGradeGenerique(spgeng);
		PMotifAvct motifAvct = new PMotifAvct();
		motifAvct.setCodeAvct("AD");
		spgradn.setMotifAvct(motifAvct);

		// When
		GradeDto dto = new GradeDto(spgradn);

		// Then
		assertEquals(spgradn.getClasse().getCodcla(), dto.getCodeClasse());
		assertEquals(spgradn.getEchelon().getCodEch(), dto.getCodeEchelon());
		assertEquals(spgradn.getCdgrad(), dto.getCodeGrade());
		assertEquals(spgradn.getGradeGenerique().getCdgeng(), dto.getCodeGradeGenerique());
		assertEquals(spgradn.getMotifAvct().getCodeAvct(), dto.getCodeMotifAvancement());
		assertEquals(spgradn.getDureeMaximum(), dto.getDureeMaximum());
		assertEquals(spgradn.getDureeMinimum(), dto.getDureeMinimum());
		assertEquals(spgradn.getDureeMoyenne(), dto.getDureeMoyenne());
		assertEquals(spgradn.getGradeInitial(), dto.getGradeInitial());
		assertEquals(spgradn.getClasse().getLibCla(), dto.getLibelleClasse());
		assertEquals(spgradn.getEchelon().getLibEch(), dto.getLibelleEchelon());
		assertEquals(spgradn.getLiGrad(), dto.getLibelleGrade());
	}
}
