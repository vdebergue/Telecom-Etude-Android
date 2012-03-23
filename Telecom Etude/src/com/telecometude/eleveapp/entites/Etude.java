package com.telecometude.eleveapp.entites;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Classe pour représenter une étude, l'id correspond à celle dans la bdd
 * 
 * @author Vince
 * 
 */
public class Etude implements Serializable, Comparable<Etude> {

	private static final long	serialVersionUID	= 1L;
	private int					numEtude;
	private String				domaine;
	private String				description;
	private int					admin;
	private boolean				nouvelle;

	/**
	 * Crée un objet étude à partir d'un objet Json.
	 * 
	 * @param objet
	 */
	public Etude(JSONObject objet) {
		try {
			setNumEtude(Integer.parseInt(objet.getString("noetude")));
			setDomaine(objet.getString("domaine"));
			setDescription(objet.getString("description"));
			setAdmin(Integer.parseInt(objet.getString("noadministrateur")));
		} catch (JSONException e) {
			Log.w("json", "Erreur constructeur Etude");
		}
	}

	public int getNumEtude() {
		return numEtude;
	}

	public void setNumEtude(int numEtude) {
		this.numEtude = numEtude;
	}

	public String getDomaine() {
		return domaine;
	}

	public void setDomaine(String domaine) {
		if (domaine == "null")
			this.domaine = "";
		else
			this.domaine = domaine;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		if (description == "null")
			this.description = "";
		else
			this.description = description;
	}

	/**
	 * Renvoie l'admin de l'étude à partir d'une liste d'admin. Si l'admin n'est pas dans la liste,
	 * cela renvoie un utilisateur bidon
	 * 
	 * @param liste
	 * @return Administrateur
	 */
	public Administrateur getAdmin(ArrayList<Administrateur> liste) {
		for (Administrateur admin : liste) {
			if (admin.getId() == this.admin)
				return admin;
		}
		return new Administrateur();
	}

	public void setAdmin(int i) {
		this.admin = i;
	}

	public boolean isNouvelle() {
		return nouvelle;
	}

	public void setNouvelle(boolean nouvelle) {
		this.nouvelle = nouvelle;
	}

	public int compareTo(Etude another) {
		return this.numEtude - another.getNumEtude();
	}

}
