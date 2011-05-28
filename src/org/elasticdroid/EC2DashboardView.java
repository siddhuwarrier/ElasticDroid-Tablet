package org.elasticdroid;

import org.elasticdroid.fragments.RegionFragment.OnRegionSelectedListener;
import org.elasticdroid.tpl.GenericActivity;

import android.os.Bundle;

public class EC2DashboardView extends GenericActivity implements OnRegionSelectedListener {
	@Override
	public void onCreate(Bundle saveState) {
    	super.onCreate(saveState);
    	setContentView(R.layout.dashboard);
	}

	public void onRegionSelected(String regionApiName, String regionEndpoint) {	
	}
}
