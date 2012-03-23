package com.telecometude.eleveapp.application;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import com.telecometude.eleveapp.R;
import com.telecometude.eleveapp.actionbar.ActionBarActivity;
import com.telecometude.eleveapp.alarmmanager.Alarm;

/**
 * Activité pour les préférences de l'application TODO Changer en PreferenceActivity et le Layout en
 * Preference Category
 * 
 * @author Vince
 * 
 */
public class ParametersActivity extends ActionBarActivity {

	CheckBox	switchRafraichir;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.parametres);
		switchRafraichir = (CheckBox) findViewById(R.id.param_checkbox);
		switchRafraichir.setChecked(Alarm.isRefreashable(this));
	}

	public void onClickMethod(View v) {
		switch (v.getId()) {
		case R.id.param_checkbox:
			Alarm.setRefreashable(this, switchRafraichir.isChecked());
			break;
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.parametres, menu);

		// Calling super after populating the menu is necessary here to ensure
		// that the
		// action bar helpers have a chance to handle this event.
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			startActivity(new Intent(this, MenuActivity.class));
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
