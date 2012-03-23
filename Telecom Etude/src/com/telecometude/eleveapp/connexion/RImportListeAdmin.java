package com.telecometude.eleveapp.connexion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.telecometude.eleveapp.entites.Administrateur;
import com.telecometude.eleveapp.entites.Constantes;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

/**
 * Classe utilisée pour importer la liste des admins. On peut soit la lancer dans un new Thread pour
 * aller chercher les infos sur le serveur, soit récupérer les infos à partir du fichier stocké en
 * local dans l'appli
 * 
 * @author Vince
 * 
 */
public class RImportListeAdmin implements Runnable {

	public Context	context;
	public Bundle	bundle;
	public boolean	forced	= false;

	public RImportListeAdmin(Context ctx) {
		this.context = ctx;
	}

	/**
	 * Renvoie la liste des admins stocké dans le fichier de l'application
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Administrateur> getListeAdmins(Context context) {
		ArrayList<Administrateur> liste = null;
		try {
			FileInputStream fis = context.openFileInput(Constantes.FICHIER_LISTE_ADMINS);
			ObjectInputStream o = new ObjectInputStream(fis);
			liste = (ArrayList<Administrateur>) o.readObject();
			o.close();
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return liste;
	}

	public void run() {
		String fileName = Constantes.FICHIER_LISTE_ADMINS;
		File file = context.getFileStreamPath(fileName);
		if (!file.exists() || forced) {
			JSONArray jArray = MethodesServeur.retour_json("jetmen.php");

			if (jArray == null) {
				return;
			}
			ArrayList<Administrateur> list = new ArrayList<Administrateur>();

			try {
				FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
				ObjectOutputStream o = new ObjectOutputStream(fos);

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					Administrateur admin = new Administrateur(json_data);
					list.add(admin);
				}

				o.writeObject(list);
				o.close();
				fos.close();
			} catch (JSONException e) {
				// Log.e("log_tag", "Error parsing data " + e.toString());
			} catch (FileNotFoundException e) {
				Log.e("atez", "Error opening the file " + fileName);
			} catch (IOException e) {
				Log.e("atez", "Error writing the object in the file: " + fileName);

			}
		}
	}
}
