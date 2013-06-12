package com.mwr.dz;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
	
	public static final String PWN_INTENT = "com.mwr.dz.PWN";

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent();
		i.setComponent(new ComponentName("com.mwr.dz", "com.mwr.dz.Agent"));
		
		context.startService(i);
	}

}
