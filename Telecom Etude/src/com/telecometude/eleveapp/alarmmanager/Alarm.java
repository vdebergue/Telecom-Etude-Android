package com.telecometude.eleveapp.alarmmanager;

import com.telecometude.eleveapp.connexion.RImportEtudes;
import com.telecometude.eleveapp.connexion.RImportListeAdmin;
import com.telecometude.eleveapp.entites.Constantes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.PowerManager;
import android.util.Log;

/**
 * Classe pour gérer l'alarme de l'application. Tous les ALARM_PERIOD, l'appli se connecte au
 * serveur pour reprendre la liste des études
 * 
 * @author Vince
 * 
 */
public class Alarm extends BroadcastReceiver {

	/**
	 * Défini l'intervalle entre deux rafraichissement des données
	 */
	static final long		ALARM_PERIOD	= AlarmManager.INTERVAL_DAY;
	/**
	 * Défini s'il faut rafraichir les données ou pas, true par défault.
	 */
	private static boolean	rafraichir		= true;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (rafraichir) {
			PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
			PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "YOUR TAG");
			wl.acquire();
			// Rafraichi les infos avec le serveur :
			RImportListeAdmin importelistadmin = new RImportListeAdmin(context);
			importelistadmin.forced = true;
			importelistadmin.run();
			new RImportEtudes(context, true).run();

			wl.release();
		}
	}

	public static void setAlarm(Context context) {
		if (rafraichir) {
			Log.d("atez", "Set Alarm");
			Alarm.cancelAlarm(context);
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			Intent i = new Intent(context, Alarm.class);
			PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_CANCEL_CURRENT);
			am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), ALARM_PERIOD, pi);
		}
	}

	public static void cancelAlarm(Context context) {
		Intent intent = new Intent(context, Alarm.class);
		PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
	}

	public static boolean isRefreashable(Context context) {
		SharedPreferences settings = context.getSharedPreferences(Constantes.FICHIER_PREFERENCES, 0);
		rafraichir = settings.getBoolean("rafraichir", true);
		return rafraichir;
	}

	public static void setRefreashable(Context context, boolean rafraichir) {
		SharedPreferences settings = context.getSharedPreferences(Constantes.FICHIER_PREFERENCES, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean("rafraichir", rafraichir);
		editor.commit();
		if (!rafraichir)
			Alarm.cancelAlarm(context);
		else
			Alarm.setAlarm(context);
	}
}
