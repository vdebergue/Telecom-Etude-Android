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
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import com.telecometude.eleveapp.R;
import com.telecometude.eleveapp.application.ListeEtudeActivity;
import com.telecometude.eleveapp.entites.Constantes;
import com.telecometude.eleveapp.entites.Etude;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Classe utilisée pour importer la liste des études On peut soit la lancer dans un new Thread pour
 * aller chercher les infos sur le serveur, soit récupérer les infos à partir du fichier stocké en
 * local dans l'appli
 * 
 * @author Vince
 * 
 */
public class RImportEtudes implements Runnable {

	boolean				forced		= false;
	Context				context;
	static final String	fileName	= Constantes.FICHIER_LISTE_ETUDES;
	File				file;
	ArrayList<Etude>	listeEtudes	= null;

	/**
	 * Constructeur de la classe où on peut forcer la récupération de la liste. Par défault, si un
	 * fichier est déjà présent, on ne va pas se connecter au serveur
	 * 
	 * @param context
	 * @param forced
	 */
	public RImportEtudes(Context context, boolean forced) {
		this.forced = forced;
		initialize(context);
	}

	/**
	 * Constructeur de la classe. Si un fichier des études est déjà présent, on ne va pas se
	 * connecter au serveur
	 * 
	 * @param context
	 */
	public RImportEtudes(Context context) {
		initialize(context);
	}

	private void initialize(Context context) {
		this.context = context;
		file = context.getFileStreamPath(fileName);

		// Si le fichier existe on va charger l'ancienne liste
		if (file.exists()) {
			listeEtudes = RImportEtudes.getListeEtude(context);
		}
	}

	public void run() {
		if (!file.exists() || forced) {
			// Soit le fichier n'existe pas, soit on force la connection

			// On récupère un JSONArray sur le serveur
			JSONArray jArray = MethodesServeur.retour_json("etudesencours.php");

			if (jArray == null) {
				Log.d("atez", "Pas de retour du serveur");
				return;
			}
			ArrayList<Etude> nouvelleListe = new ArrayList<Etude>();
			int numNouvellesEtudes=0;
			try {
				FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
				ObjectOutputStream o = new ObjectOutputStream(fos);

				for (int i = 0; i < jArray.length(); i++) {
					JSONObject json_data = jArray.getJSONObject(i);
					// On crée les objets à partir des objet JSON
					Etude etude = new Etude(json_data);
					nouvelleListe.add(etude);
				}
				Collections.sort(nouvelleListe);

				
				if( listeEtudes != null){
				// dire si une étude est nouvellle ou pas en comparant les
				// études dans les deux listes
				// Si nouveaux éléments, afficher une notification
	
					//on récupére le plus grand num de l'ancienne liste d'étude : 
					int maxAnciennesEtudes = 0;
					for(Etude etude : listeEtudes){
						int num = etude.getNumEtude();
						if(num>maxAnciennesEtudes) maxAnciennesEtudes = num;
					}
					int maxNouvellesEtudes = nouvelleListe.get(listeEtudes.size()-1).getNumEtude();
					
					if(maxNouvellesEtudes > maxAnciennesEtudes) {
						//On a des nouvelles études :
						for(Etude etude : nouvelleListe){
							if(etude.getNumEtude()>maxAnciennesEtudes) {
								numNouvellesEtudes ++;
								etude.setNouvelle(true);
							}
						}
					}
				}

				// On écrit la liste des études dans le fichier
				o.writeObject(nouvelleListe);
				o.close();
				fos.close();

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//Notification si nouvelles Etudes:
			//if(numNouvellesEtudes>0){
			if(true){
				String ns = Context.NOTIFICATION_SERVICE;
				NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(ns);
				String text = context.getString(R.string.notif_text);
				Notification notif = new Notification(R.drawable.ic_notif,text,System.currentTimeMillis());
				
				CharSequence contentTitle = context.getText(R.string.app_name);
				CharSequence contentText = context.getString(R.string.notif_debut) +" "+ numNouvellesEtudes +" "+ context.getString(R.string.notif_fin);
				Intent notificationIntent = new Intent(context, ListeEtudeActivity.class);
				PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0);

				notif.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
				
				mNotificationManager.notify(Constantes.NOTIFICATION_ID, notif);
			}
			
			
		}
	}

	/**
	 * Renvoie la liste des études à partir du fichier
	 * 
	 * @param context
	 * @return ArrayList<Etude>
	 */
	@SuppressWarnings("unchecked")
	public static ArrayList<Etude> getListeEtude(Context context) {
		ArrayList<Etude> liste = null;
		try {
			FileInputStream fis = context.openFileInput(Constantes.FICHIER_LISTE_ETUDES);
			ObjectInputStream o = new ObjectInputStream(fis);
			liste = (ArrayList<Etude>) o.readObject();
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

}
