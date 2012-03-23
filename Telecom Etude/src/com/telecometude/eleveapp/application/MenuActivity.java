package com.telecometude.eleveapp.application;

import java.util.ArrayList;

import com.telecometude.eleveapp.R;
import com.telecometude.eleveapp.actionbar.ActionBarActivity;
import com.telecometude.eleveapp.connexion.RImportEtudes;
import com.telecometude.eleveapp.connexion.RImportListeAdmin;
import com.telecometude.eleveapp.entites.Etude;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Menu principal de l'appli TODO: corriger le layout en mode horizontal
 * 
 * @author Vince
 * 
 */
public class MenuActivity extends ActionBarActivity {

	ImageButton	imageButtonListeAdmins;
	ImageButton	imageButtonListeEtudes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_dashboard);

		boolean listeAdminVide = getIntent().getBooleanExtra("listeadminvide", false);
		if (listeAdminVide) {
			CharSequence txt = getString(R.string.listeadminvide);
			Toast toast = Toast.makeText(this, txt, Toast.LENGTH_LONG);
			toast.show();
		}

		boolean listeEtudeVide = getIntent().getBooleanExtra("listeetudevide", false);
		if (listeEtudeVide) {
			CharSequence txt = getString(R.string.listeetudevide);
			Toast toast = Toast.makeText(this, txt, Toast.LENGTH_LONG);
			toast.show();
		}
	}

	public void onClickFeature(View v) {
		switch (v.getId()) {
		case R.id.imageButtonListeAdmins:
			Intent intent = new Intent();
			intent.setClass(this, ListeAdminActivity.class);
			startActivity(intent);
			break;
		case R.id.home_etudes:
			ArrayList<Etude> listeEtudes = RImportEtudes.getListeEtude(this);
			if(listeEtudes!=null) Toast.makeText(this, "Taille liste etude: " + listeEtudes.size(), Toast.LENGTH_LONG).show();
			Intent i = new Intent();
			i.setClass(this, ListeEtudeActivity.class);
			startActivity(i);
			break;
		default:
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.main, menu);

		// Calling super after populating the menu is necessary here to ensure
		// that the
		// action bar helpers have a chance to handle this event.
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Toast.makeText(this, "Tapped home", Toast.LENGTH_SHORT).show();
			break;

		case R.id.menu_refresh:
			Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
			getActionBarHelper().setRefreshActionItemState(true);
			getWindow().getDecorView().postDelayed(new Runnable() {
				public void run() {
					getActionBarHelper().setRefreshActionItemState(false);
				}
			}, 1000);
			RImportListeAdmin thr = new RImportListeAdmin(getApplicationContext());
			thr.forced = true;
			new Thread(thr).start();
			break;

		case R.id.menu_search:
			Toast.makeText(this, "Rafraichir etudes", Toast.LENGTH_SHORT).show();
			new Thread(new RImportEtudes(this, true)).start();
			break;

		case R.id.menu_parameters:
			Intent intent = new Intent(this, ParametersActivity.class);
			startActivity(intent);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
