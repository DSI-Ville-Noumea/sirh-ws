package nc.noumea.mairie.web.dto.avancements;

import java.util.ArrayList;
import java.util.List;

public class ChangementClasseDto {

	private String A;
	private String cap;

	private List<ChangementClasseItemDto> changementClasseItems;

	private List<String> membresPresents;
	private List<String> representants;
	private String president;

	public ChangementClasseDto() {
		changementClasseItems = new ArrayList<ChangementClasseItemDto>();
		membresPresents = new ArrayList<String>();
		representants = new ArrayList<String>();
	}

	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public List<ChangementClasseItemDto> getChangementClasseItems() {
		return changementClasseItems;
	}

	public void setChangementClasseItems(
			List<ChangementClasseItemDto> changementClasseItems) {
		this.changementClasseItems = changementClasseItems;
	}

	public List<String> getMembresPresents() {
		return membresPresents;
	}

	public void setMembresPresents(List<String> membresPresents) {
		this.membresPresents = membresPresents;
	}

	public List<String> getRepresentants() {
		return representants;
	}

	public void setRepresentants(List<String> representants) {
		this.representants = representants;
	}

	public String getPresident() {
		return president;
	}

	public void setPresident(String president) {
		this.president = president;
	}
}
