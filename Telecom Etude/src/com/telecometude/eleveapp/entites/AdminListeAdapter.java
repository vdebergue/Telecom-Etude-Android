package com.telecometude.eleveapp.entites;

import java.util.ArrayList;

import com.telecometude.eleveapp.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Classe utilisée pour afficher une liste d'admin dans une ListView
 * 
 * @author Vince
 * 
 */
public class AdminListeAdapter extends ArrayAdapter<Administrateur> {

	private ArrayList<Administrateur>	items;
	private Context						cxt;

	public AdminListeAdapter(Context context, int textViewResourceId, ArrayList<Administrateur> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.cxt = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.adminview, null);
		}
		Administrateur m = items.get(position);
		if (m != null) {
			TextView nom = (TextView) v.findViewById(R.id.textNom);
			TextView mail = (TextView) v.findViewById(R.id.textMail);
			// ImageView img = (ImageView) v.findViewById(R.id.imgAdmin);
			nom.setText(m.getNom() + " " + m.getPrenom());
			mail.setText(m.getMail());
			// img.setImageURI(uri);
		}
		return v;
	}

}
