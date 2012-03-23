package com.telecometude.eleveapp.application;

import java.util.ArrayList;
import java.util.Collections;

import com.telecometude.eleveapp.R;
import com.telecometude.eleveapp.connexion.RImportEtudes;
import com.telecometude.eleveapp.entites.Constantes;
import com.telecometude.eleveapp.entites.Etude;
import com.telecometude.eleveapp.entites.EtudeListeAdapter;

import android.app.ListActivity;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

/**
 * Affiche la liste des études TODO : corriger l'action bar TODO : ajouter des actions lors de
 * pressions sur les études
 * 
 * @author Vince
 * 
 */
public class ListeEtudeActivity extends ListActivity {

	ArrayList<Etude>	liste;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.etudelist);

		// On récupère la liste des études
		liste = RImportEtudes.getListeEtude(this);

		if (liste == null) {
			Log.e("atez", "La liste des etudes est vide");
			new Thread(new RImportEtudes(this, true)).start();
			Intent intent = new Intent(this, MenuActivity.class);
			intent.putExtra("listeetudevide", true);
			startActivity(intent);
			finish();
			return;
		}
		Collections.reverse(liste);
		setListAdapter(new EtudeListeAdapter(this, android.R.id.list, liste));
	}

	@Override
	protected void onResume() {
		super.onResume();
		//Enlève la notification pour les nouvelles études:
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager)getSystemService(ns);
		mNotificationManager.cancel(Constantes.NOTIFICATION_ID);
	}
	
	

}
