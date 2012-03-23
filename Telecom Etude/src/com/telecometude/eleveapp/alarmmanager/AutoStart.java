package com.telecometude.eleveapp.alarmmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Classe qui redéfini l'alarme au reboot de l'application
 * 
 * @author Vince
 * 
 */
public class AutoStart extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Alarm.setAlarm(context);
		}
	}

}
