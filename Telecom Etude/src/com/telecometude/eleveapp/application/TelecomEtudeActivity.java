package com.telecometude.eleveapp.application;

import java.util.Timer;
import java.util.TimerTask;

import com.telecometude.eleveapp.R;
import com.telecometude.eleveapp.alarmmanager.Alarm;
import com.telecometude.eleveapp.connexion.RImportEtudes;
import com.telecometude.eleveapp.connexion.RImportListeAdmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * Point d'entré de l'application. Affiche simplement un logo puis passe à MenuActivity au bout de
 * 2s ou lors d'un click
 * 
 * @author Vince
 * 
 */
public class TelecomEtudeActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		View back = findViewById(R.id.backgroundMain);
		back.setClickable(true);
		back.setOnClickListener(listener);

		// Lancer un Thread qui va se connecter au serveur et télécharger les
		// infos
		RImportListeAdmin connexion = new RImportListeAdmin(getApplicationContext());
		connexion.bundle = savedInstanceState;
		new Thread(connexion).start();
		new Thread(new RImportEtudes(this)).start();
		// Set Alarm
		Alarm.setAlarm(this);

	}

	@Override
	public void onStart() {
		super.onStart();

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				lauchMenu();
			}
		};
		Timer timer = new Timer();
		timer.schedule(task, 2000);
	}

	private void lauchMenu() {
		Intent intent = new Intent();
		intent.setClass(TelecomEtudeActivity.this, MenuActivity.class);
		startActivity(intent);
		finish();
	}

	OnClickListener	listener	= new OnClickListener() {

									public void onClick(View v) {
										if (v == findViewById(R.id.backgroundMain)) {
											lauchMenu();
										}
									}
								};

}