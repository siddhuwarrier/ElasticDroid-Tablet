package org.elasticdroid;

import org.elasticdroid.tpl.GenericActivity;

import android.os.Bundle;

public class EC2DashboardView extends GenericActivity {
	@Override
	public void onCreate(Bundle saveState) {
    	super.onCreate(saveState);
    	setContentView(R.layout.dashboard);
	}
}
