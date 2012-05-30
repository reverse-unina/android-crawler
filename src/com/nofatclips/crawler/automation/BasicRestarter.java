package com.nofatclips.crawler.automation;

import com.nofatclips.crawler.model.Restarter;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;

public class BasicRestarter implements Restarter {

	private ContextWrapper main;

	@Override
	public void restart() {
		Intent i = this.main.getBaseContext().getPackageManager().getLaunchIntentForPackage(this.main.getBaseContext().getPackageName() );
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
		this.main.startActivity(i);
	}
	
	@Override
	public void setRestartPoint(Activity a) {
		this.main = new ContextWrapper(a);
	}
	
}