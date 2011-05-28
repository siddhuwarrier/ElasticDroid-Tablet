package org.elasticdroid.fragments;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticdroid.ElasticDroidApp;
import org.elasticdroid.R;
import org.elasticdroid.intf.callback.RetrieveRegionCallbackIntf;
import org.elasticdroid.task.RetrieveRegionTask;

import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

/**
 * A fragment showing the list of regions
 * @author siddhu.warrier
 *
 */
public class RegionFragment extends ListFragment implements RetrieveRegionCallbackIntf {
	/**
	 * Reference to RetrieveRegionModel object
	 */
	private RetrieveRegionTask retrieveRegionTask;
	
	/** the available AWS regions */
	private HashMap<String, String> regionData;
	
	private static final String TAG = RegionFragment.class.getName();
	
	@Override
	public void onCreate(Bundle savedState) {
		super.onCreate(savedState);
		setRetainInstance(true);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
    	return inflater.inflate(R.layout.regions, container, false);
    }
	
	/**
	 * 
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
        
		setListAdapter(new RegionsAdapter(
				getActivity(), 
				R.layout.regionrow, 
				Arrays.asList(new String[]{"EU West", "US East", "US West", "AP Northeast"})));
	}
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (retrieveRegionTask == null) {
			Log.d(TAG, "Region data null. Start background processing...");
			
			retrieveRegionTask = new RetrieveRegionTask(
					getActivity(),
					this, 
					((ElasticDroidApp) getActivity().getApplication()).getAwsUserModel());
			
			retrieveRegionTask.execute();
		}
	}

	public void cleanUpAfterTaskExecution() {
		// TODO Auto-generated method stub
		
	}

	public void awsClientException(AmazonClientException exception) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Amazon Client Exception!");
	}

	public void awsServerException(AmazonServiceException exception) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Amazon Service Exception!");
	}

	public void regionsRetrieved(Map<String, String[]> regions) {
		// TODO Auto-generated method stub
		Log.d(TAG, "Regions retrieved!");
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
