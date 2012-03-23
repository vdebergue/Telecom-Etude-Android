package com.telecometude.eleveapp.entites;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

/**
 * Classe pour représenter un administrateur L'id correspond à celle de la BDD TODO gestion tel et
 * image d'un admin
 * 
 * @author Vince
 * 
 */
public class Administrateur implements Comparable<Administrateur>, Serializable {

	private static final long	serialVersionUID	= 1L;
	private int					id;
	private String				nom;
	private String				prenom;
	private String				mail;
	private String				promo;
	private String				fonction;
	private String				tel;

	/**
	 * Crée un nouvel administrateur à partir d'un objet JSON contenant différents champs les champs
	 * sont ceux de la bdd
	 * 
	 * @param objet
	 */
	public Administrateur(JSONObject objet) {
		if (objet != null) {
			try {
				setId(Integer.parseInt(objet.getString("noadministrateur")));
				setNom(objet.getString("nom"));
				setPrenom(objet.getString("prenom"));
				setMail(objet.getString("mailje"));
				setFonction(objet.getString("superadmin"));
				setTel("+33 6 34 76 " + id);
				setPromo(objet.getString("promo"));

			} catch (JSONException e) {
				Log.w("json", "Erreur constructeur objet Administrateur");
			}
		}

	}

	/**
	 * Crée un admin par défault utilisé si l'admin d'une étude n'est plus à la JE
	 */
	public Administrateur() {
		this.fonction = "Retraité";
		this.nom = "Vieux";
		this.prenom = "Con";
		this.mail = "";
		this.tel = "";
		this.promo = "Trop vieille pour s'en souvenir";
		this.id = -1;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPromo() {
		return promo;
	}

	public void setPromo(String promo) {
		this.promo = promo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFonction() {
		return fonction;
	}

	/**
	 * Prend en entrée en string (peut être "3") et le convertit en la fonction qu'il représente
	 * ("Trésorier")
	 * 
	 * @param String
	 *            fonction
	 */
	public void setFonction(String fonction) {
		int superadmin = Integer.parseInt(fonction);
		switch (superadmin) {
		case 1:
			this.fonction = "Pole Info";
			break;
		case 2:
			this.fonction = "Présidence";
			break;
		case 3:
			this.fonction = "Tresorier";
			break;
		case 4:
			this.fonction = "Secrétaire général";
			break;
		case 5:
			this.fonction = "Pole Qualité";
			break;
		case 0:
			this.fonction = "Pole Communication";
			break;
		default:
			this.fonction = fonction;
			break;
		}
	}

	public int compareTo(Administrateur another) {
		return this.nom.compareTo(another.getNom());
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

}
