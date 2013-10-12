package com.mwr.dz;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.view.Menu;

public class PwnActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Uri server_uri = this.getIntent().getData();
		if(server_uri.getScheme().equals("pwn")) {
			Intent i = new Intent();
			i.setComponent(new ComponentName("com.mwr.dz", "com.mwr.dz.Agent"));
			
			this.getApplicationContext().startService(i);
		}
		
		this.finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return false;
	}

}
