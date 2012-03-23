package com.telecometude.eleveapp.application;

import java.util.ArrayList;
import java.util.Collections;

import com.telecometude.eleveapp.R;
import com.telecometude.eleveapp.connexion.RImportListeAdmin;
import com.telecometude.eleveapp.entites.AdminListeAdapter;
import com.telecometude.eleveapp.entites.Administrateur;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

/**
 * Activity pour afficher la liste des admins TODO : corriger l'action bar en haut de l'activité
 * TODO : Long press action
 * 
 * @author Vince
 * 
 */
public class ListeAdminActivity extends ListActivity {

	ArrayList<Administrateur>	liste;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminlist);

		// obtenir la liste des admins à prtir du fichier
		liste = RImportListeAdmin.getListeAdmins(this);

		if (liste == null) {
			Log.e("atez", "La liste des administrateurs est vide");

			// Si la liste est vide, on tente de la retélécharger du serveur et on revient au Menu
			RImportListeAdmin run = new RImportListeAdmin(getApplicationContext());
			run.bundle = savedInstanceState;
			run.forced = true;
			new Thread(run).start();
			Intent intent = new Intent(ListeAdminActivity.this, MenuActivity.class);
			intent.putExtra("listeadminvide", true);
			startActivity(intent);
			finish();
			return;
		}
		Log.d("atez", "Taille liste: " + liste.size());
		Collections.sort(liste);

		setListAdapter(new AdminListeAdapter(this, android.R.id.list, liste));
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		Administrateur admin = (Administrateur) getListAdapter().getItem(position);
		Intent intent = new Intent();
		intent.putExtra("admin", admin);
		intent.setClass(ListeAdminActivity.this, AdminDetailActivity.class);
		startActivity(intent);
	}

}
