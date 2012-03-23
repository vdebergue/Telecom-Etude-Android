package com.telecometude.eleveapp.entites;

import java.util.ArrayList;

import com.telecometude.eleveapp.R;
import com.telecometude.eleveapp.connexion.RImportListeAdmin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Classe pour Adapter une liste d'étude à une list View. Le layout de item est etudelistview.xml
 * 
 * @author Vince
 * 
 */
public class EtudeListeAdapter extends ArrayAdapter<Etude> {

	private ArrayList<Etude>			items;
	private ArrayList<Administrateur>	listeAdmin;
	private Context						cxt;

	public EtudeListeAdapter(Context context, int textViewResourceId, ArrayList<Etude> items) {
		super(context, textViewResourceId, items);
		this.items = items;
		this.cxt = context;
		listeAdmin = RImportListeAdmin.getListeAdmins(getContext());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) cxt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.etudelistview, null);
		}
		Etude m = items.get(position);
		if (m != null) {
			// TODO finir l'affichage d'une étude
			Administrateur admininistrateur = m.getAdmin(listeAdmin);
			TextView nom = (TextView) v.findViewById(R.id.etude_nom);
			TextView admin = (TextView) v.findViewById(R.id.etude_admin);
			TextView description = (TextView) v.findViewById(R.id.etude_description);
			nom.setText(m.getDomaine());
			admin.setText(admininistrateur.getNom() + " " + admininistrateur.getPrenom());
			description.setText(m.getDescription());
		}
		return v;
	}

}
