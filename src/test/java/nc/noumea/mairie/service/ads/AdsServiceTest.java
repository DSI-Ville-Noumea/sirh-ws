package nc.noumea.mairie.service.ads;

import static org.junit.Assert.*;
import nc.noumea.mairie.web.dto.EntiteDto;
import nc.noumea.mairie.web.dto.ReferenceDto;
import nc.noumea.mairie.ws.ADSWSConsumer;

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
	
	@Test
	public void getAffichageDirectionWithoutCallADS_returnResult() {
		
		EntiteDto root = new EntiteDto();
		root.setIdEntite(0);
		root.setSigle("root");
		
		EntiteDto dsi = new EntiteDto();
		dsi.setIdEntite(1);
		dsi.setSigle("dsi");
		dsi.setEntiteParent(root);
		dsi.setTypeEntite(new ReferenceDto());
		dsi.getTypeEntite().setLabel(ADSWSConsumer.AFFICHAGE_DIRECTION);
		root.getEnfants().add(dsi);
		
		EntiteDto sie = new EntiteDto();
		sie.setIdEntite(2);
		sie.setSigle("sie");
		sie.setEntiteParent(dsi);
		dsi.getEnfants().add(sie);
		
		EntiteDto sed = new EntiteDto();
		sed.setIdEntite(3);
		sed.setSigle("sed");
		sed.setEntiteParent(dsi);
		dsi.getEnfants().add(sed);
		
		EntiteDto sedDmd = new EntiteDto();
		sedDmd.setIdEntite(4);
		sedDmd.setSigle("sedDmd");
		sedDmd.setEntiteParent(dsi);
		sed.getEnfants().add(sedDmd);
		
		AdsService service = new AdsService();
		EntiteDto result = service.getAffichageDirectionWithoutCallADS(sedDmd);
		
		assertEquals(result.getSigle(), "dsi");
	}
	
	@Test
	public void getAffichageDirectionWithoutCallADS_returnNull() {
		
		EntiteDto root = new EntiteDto();
		root.setIdEntite(0);
		root.setSigle("root");
		
		EntiteDto dsi = new EntiteDto();
		dsi.setIdEntite(1);
		dsi.setSigle("dsi");
		dsi.setEntiteParent(root);
		root.getEnfants().add(dsi);
		
		EntiteDto sie = new EntiteDto();
		sie.setIdEntite(2);
		sie.setSigle("sie");
		sie.setEntiteParent(dsi);
		dsi.getEnfants().add(sie);
		
		EntiteDto sed = new EntiteDto();
		sed.setIdEntite(3);
		sed.setSigle("sed");
		sed.setEntiteParent(dsi);
		dsi.getEnfants().add(sed);
		
		EntiteDto sedDmd = new EntiteDto();
		sedDmd.setIdEntite(4);
		sedDmd.setSigle("sedDmd");
		sedDmd.setEntiteParent(dsi);
		sed.getEnfants().add(sedDmd);
		
		AdsService service = new AdsService();
		EntiteDto result = service.getAffichageDirectionWithoutCallADS(sedDmd);
		
		assertNull(result);
	}
}
