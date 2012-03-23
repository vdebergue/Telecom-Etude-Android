package com.telecometude.eleveapp.connexion;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import com.telecometude.eleveapp.entites.Constantes;

import android.util.Log;

/**
 * Classe pour gérer les connexions avec le serveur
 * 
 * @author Vince
 * 
 */
public class MethodesServeur {

	/**
	 * Prend en entrée un nom de fichier sur le serveur et renvoie un JSONArray du fichier sur le
	 * serveur L'URL du serveur est fixé dans l'appli
	 * 
	 * @param nomFichierPHP
	 * @return
	 */
	public final static JSONArray retour_json(String nomFichierPHP) {
		final String URL = Constantes.URL_SERVEUR + Constantes.REPERTOIRE_FICHIERS_ANDROID;
		String strURL = URL + nomFichierPHP;
		String result = "";
		InputStream is = null;

		// Envoyer la requete au script PHP.
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(strURL);
			System.out.println("connexion to: " + strURL + "...");

			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		} catch (Exception e) {
			Log.e("log_tag", "Erreur connection Http: " + e.toString());
			e.printStackTrace();
			return null;
		}

		// Conversion de la requete en string
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");

			}

			is.close();
			result = sb.toString();
		} catch (Exception e) {

			Log.e("log_tag", "Error converting result " + e.toString());
		}

		JSONArray jArray = null;
		try {
			jArray = new JSONArray(result);
		} catch (JSONException e) {
			e.printStackTrace();
			Log.e("log_tag", "Erreur JSONArray " + e.toString());
		}

		return jArray;
	}
}
