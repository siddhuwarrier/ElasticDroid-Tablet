package org.elasticdroid.fragments;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.elasticdroid.R;
import org.elasticdroid.task.RetrieveRegionTask;

import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A fragment showing the list of regions
 * @author siddhu.warrier
 *
 */
public class RegionFragment extends ListFragment {
	/**
	 * Reference to RetrieveRegionModel object
	 */
	private RetrieveRegionTask retrieveRegionTask;
	
	/** the available AWS regions */
	private HashMap<String, String> regionData;
	
	/** The connectivity data */
	private HashMap<String, String> connectionData;
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.regions, container, false);
    }
	
	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		Intent intent = getActivity().getIntent();
		this.connectionData = (HashMap<String, String>) intent.getSerializableExtra("org.elasticdroid.LoginView.connectionData");
		
		setListAdapter(new RegionsAdapter(
				getActivity(), 
				R.layout.regionrow, 
				Arrays.asList(new String[]{"EU West", "US East", "US West", "AP Northeast"})));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		//check if we need to get region data.
		//if we have neither region data, nor a running retrieveregionmodel
		//execute retrieve region model
		/*if (regionData == null) {
			//execute retrieveregionmodel unless not executing already
			if (retrieveRegionTask == null) {
				retrieveRegionTask = new RetrieveRegionTask();
				retrieveRegionTask.execute(new HashMap<?,?>[]{connectionData});
			}
		}*/
	}
}

/**
 * Adapter to display the instances in a list view. 
 * @author Siddhu Warrier
 *
 * 6 Dec 2010
 */
class RegionsAdapter extends ArrayAdapter<String>{
	/** Instance list */
	private List<String> regions;
	/** Context; typically the Activity that sets an object of this class as the Adapter */
	private Context context;
	
	/**
	 * Adapter constructor
	 * @param context The context to display this in
	 * @param textViewResourceId 
	 * @param instanceData
	 * @param listType
	 */
	public RegionsAdapter(Context context, int textViewResourceId, 
			List<String> regions) {
		super(context, textViewResourceId, regions);
		
		this.context = context;
		this.regions = regions;
	}
	
	/**
	 * 
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View regionRow = convertView;
		
		if (regionRow == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService
			(Context.LAYOUT_INFLATER_SERVICE);
		
			regionRow = inflater.inflate(R.layout.regionrow, parent, false);
		}
	
		//get text view widgets
		TextView textViewHeadline = (TextView)regionRow.findViewById(R.id.regionName);
		
		textViewHeadline.setText(regions.get(position));
		
		return regionRow; //return the populated row to display
	}
	
	/**
	 * TODO When more secgroup functionality is added, re-enable it. 
	 * 
	 * Function to disable all items in the ListView, as we do not want users clicking on
	 * them.
	 */
	@Override
    public boolean areAllItemsEnabled() 
    { 
            return false; 
    } 
    
    /**
     * TODO When more secgroup functionality is added, re-enable it.
     * 
     * Another function that does the same as hte function above
     */
	@Override
    public boolean isEnabled(int position) 
    { 
            return false; 
    } 
}
