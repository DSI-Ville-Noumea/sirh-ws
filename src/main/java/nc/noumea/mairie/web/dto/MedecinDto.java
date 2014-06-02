package nc.noumea.mairie.web.dto;

import nc.noumea.mairie.model.bean.sirh.Medecin;

public class MedecinDto {
	private String nom;
	private String prenom;
	private String titre;

	public MedecinDto(Medecin m) {
		if (m != null) {
			this.nom = m.getNomMedecin();
			this.prenom = m.getPrenomMedecin();
			this.titre = m.getTitreMedecin();
		}
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

}
