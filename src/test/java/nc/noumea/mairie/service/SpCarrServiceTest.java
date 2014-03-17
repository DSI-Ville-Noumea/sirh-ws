package nc.noumea.mairie.service;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import nc.noumea.mairie.model.bean.Spcarr;
import nc.noumea.mairie.model.bean.Spcatg;
import nc.noumea.mairie.model.pk.SpcarrId;
import nc.noumea.mairie.model.repository.IMairieRepository;
import nc.noumea.mairie.service.SpCarrService;
import nc.noumea.mairie.web.dto.CarriereDto;

import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

public class SpCarrServiceTest {

	@Test
	public void getCarriereFonctionnaireAncienne_1result() throws ParseException {
		// Given
		SpcarrId id = new SpcarrId();
			id.setDatdeb(20101010);
			id.setNomatr(5138);
		
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(1);
			categorie.setLibelleCategorie("libelleCategorie");
			
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setCategorie(categorie);

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
			Mockito.when(mairieRepository.getCarriereFonctionnaireAncienne(Mockito.anyInt())).thenReturn(carr);

		SpCarrService service = new SpCarrService();
			ReflectionTestUtils.setField(service, "mairieRepository", mairieRepository);

		// When
		CarriereDto result = service.getCarriereFonctionnaireAncienne(5138);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		// Then
		assertEquals(result.getCodeCategorie().intValue(), 1);
		assertEquals(result.getLibelleCategorie(), "libelleCategorie");
		assertEquals(result.getNoMatr().intValue(), 5138);
		assertEquals(result.getDateDebut(), sdf.parse("20101010"));
	}
	
	@Test
	public void getCarriereFonctionnaireAncienne_returnNull() throws ParseException {
		// Given
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
			Mockito.when(mairieRepository.getCarriereFonctionnaireAncienne(Mockito.anyInt())).thenReturn(null);

		SpCarrService service = new SpCarrService();
			ReflectionTestUtils.setField(service, "mairieRepository", mairieRepository);

		// When
		CarriereDto result = service.getCarriereFonctionnaireAncienne(5138);

		assertNull(result);
	}
	
	@Test
	public void getCarriereActive_1result() throws ParseException {
		// Given
		SpcarrId id = new SpcarrId();
			id.setDatdeb(20101010);
			id.setNomatr(5138);
		
		Spcatg categorie = new Spcatg();
			categorie.setCodeCategorie(1);
			categorie.setLibelleCategorie("libelleCategorie");
			
		Spcarr carr = new Spcarr();
			carr.setId(id);
			carr.setCategorie(categorie);

		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
			Mockito.when(mairieRepository.getCarriereActive(Mockito.anyInt())).thenReturn(carr);

		SpCarrService service = new SpCarrService();
			ReflectionTestUtils.setField(service, "mairieRepository", mairieRepository);

		// When
		CarriereDto result = service.getCarriereActive(5138);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		// Then
		assertEquals(result.getCodeCategorie().intValue(), 1);
		assertEquals(result.getLibelleCategorie(), "libelleCategorie");
		assertEquals(result.getNoMatr().intValue(), 5138);
		assertEquals(result.getDateDebut(), sdf.parse("20101010"));
	}
	
	@Test
	public void getCarriereActive_returnNull() throws ParseException {
		// Given
		IMairieRepository mairieRepository = Mockito.mock(IMairieRepository.class);
			Mockito.when(mairieRepository.getCarriereFonctionnaireAncienne(Mockito.anyInt())).thenReturn(null);

		SpCarrService service = new SpCarrService();
			ReflectionTestUtils.setField(service, "mairieRepository", mairieRepository);

		// When
		CarriereDto result = service.getCarriereActive(5138);

		assertNull(result);
	}
	
}
