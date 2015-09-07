package nc.noumea.mairie.service.ads;

import static org.junit.Assert.*;
import nc.noumea.mairie.web.dto.EntiteDto;

import org.junit.Test;

public class AdsServiceTest {

	@Test
	public void getSigleEntityInEntiteDtoTreeByIdEntite() {
		
		EntiteDto dsi = new EntiteDto();
		dsi.setIdEntite(1);
		dsi.setSigle("DSI");
		
		EntiteDto sie = new EntiteDto();
		sie.setIdEntite(2);
		sie.setSigle("SIE");
		
		EntiteDto sed = new EntiteDto();
		sed.setIdEntite(3);
		sed.setSigle("SED");
		
		EntiteDto sedDmd = new EntiteDto();
		sedDmd.setIdEntite(4);
		sedDmd.setSigle("SED-DMD");
		
		sed.getEnfants().add(sedDmd);
		
		dsi.getEnfants().add(sie);
		dsi.getEnfants().add(sed);
		
		AdsService service = new AdsService();
		
		String result = service.getSigleEntityInEntiteDtoTreeByIdEntite(dsi, 4);
		
		assertEquals(result, "SED-DMD");
	}
	
	@Test
	public void getEntiteByIdEntiteOptimiseWithWholeTree() {
		
		EntiteDto root = new EntiteDto();
		root.setIdEntite(0);
		root.setSigle("root");
		
		EntiteDto dsi = new EntiteDto();
		dsi.setIdEntite(1);
		dsi.setSigle("dsi");
		root.getEnfants().add(dsi);
		
		EntiteDto sie = new EntiteDto();
		sie.setIdEntite(2);
		sie.setSigle("sie");
		dsi.getEnfants().add(sie);
		
		EntiteDto sed = new EntiteDto();
		sed.setIdEntite(3);
		sed.setSigle("sed");
		dsi.getEnfants().add(sed);
		
		AdsService service = new AdsService();
		EntiteDto result = service.getEntiteByIdEntiteOptimiseWithWholeTree(3, root);
		
		assertEquals(result.getSigle(), "sed");
	}
}
