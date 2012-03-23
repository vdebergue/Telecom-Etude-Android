package com.telecometude.eleveapp.application;

import com.telecometude.eleveapp.R;
import com.telecometude.eleveapp.actionbar.ActionBarActivity;
import com.telecometude.eleveapp.entites.Administrateur;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Activity pour afficher les détails d'un administrateur La layout correspondant à la vue est
 * admindetail TODO : share le contact TODO : changer icone pour les sms
 * 
 * @author Vince
 * 
 */
public class AdminDetailActivity extends ActionBarActivity {

	TextView		textNom;
	TextView		textPromo;
	TextView		textMail;
	TextView		textTel;
	TextView		textFonction;
	Administrateur	admin;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.admindetail);

		// On récupère l’Intent que l’on a reçu
		Intent thisIntent = getIntent();
		admin = (Administrateur) thisIntent.getSerializableExtra("admin");

		textNom = (TextView) findViewById(R.id.textNom);
		textPromo = (TextView) findViewById(R.id.textPromo);
		textMail = (TextView) findViewById(R.id.textMail);
		textTel = (TextView) findViewById(R.id.textTel);
		textFonction = (TextView) findViewById(R.id.textFonction);

		textNom.setText(admin.getNom() + " " + admin.getPrenom());
		textPromo.setText(admin.getPromo());
		textMail.setText(admin.getMail());
		textTel.setText(admin.getTel());
		textFonction.setText(admin.getFonction());

	}

	public void onClickMethod(View v) {
		switch (v.getId()) {
		case R.id.textMail:
			final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
			String subject = getString(R.string.mail_subject);
			String body = getString(R.string.mail_body);
			emailIntent.setType("text/html");
			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { admin.getMail() });
			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(body));
			startActivity(Intent.createChooser(emailIntent, "Email:"));
			break;
		case R.id.textTel:
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse("tel:" + admin.getTel()));
			startActivity(callIntent);
			break;
		case R.id.imageSMS:
			Toast.makeText(AdminDetailActivity.this, "envoi sms ...", Toast.LENGTH_SHORT).show();
			Intent sendIntent = new Intent(Intent.ACTION_VIEW);
			sendIntent.setData(Uri.parse("sms:" + admin.getTel()));
			startActivity(sendIntent);
		default:
			break;

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.detailadmin, menu);

		// Calling super after populating the menu is necessary here to ensure
		// that the
		// action bar helpers have a chance to handle this event.
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			Intent intent = new Intent(this, MenuActivity.class);
			startActivity(intent);
			break;

		case R.id.menu_share:
			Toast.makeText(this, "Tapped share", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
