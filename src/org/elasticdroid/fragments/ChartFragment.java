package org.elasticdroid.fragments;

import java.util.Arrays;
import java.util.HashMap;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYValueSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.elasticdroid.R;
import org.elasticdroid.task.RetrieveRegionTask;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

/**
 * A fragment showing the list of regions
 * @author siddhu.warrier
 *
 */
public class ChartFragment extends Fragment {
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
    	return inflater.inflate(R.layout.dashboard_chart, container, false);
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
		
		drawChart();
	}
	
	private void drawChart() {
	    XYMultipleSeriesDataset series = new XYMultipleSeriesDataset();
	    XYValueSeries newTicketSeries = new XYValueSeries("New Tickets");
	    newTicketSeries.add(1, 2, 14);
	    newTicketSeries.add(2, 2, 12);
	    newTicketSeries.add(3, 2, 18);
	    newTicketSeries.add(4, 2, 5);
	    newTicketSeries.add(5, 2, 1);
	    series.addSeries(newTicketSeries);
	    XYValueSeries fixedTicketSeries = new XYValueSeries("Fixed Tickets");
	    fixedTicketSeries.add(1, 1, 7);
	    fixedTicketSeries.add(2, 1, 4);
	    fixedTicketSeries.add(3, 1, 18);
	    fixedTicketSeries.add(4, 1, 3);
	    fixedTicketSeries.add(5, 1, 1);
	    series.addSeries(fixedTicketSeries);

	    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	    renderer.setAxisTitleTextSize(16);
	    renderer.setChartTitleTextSize(20);
	    renderer.setLabelsTextSize(15);
	    renderer.setLegendTextSize(15);
	    renderer.setRange(new double[]{0, 6, 0, 6});
	    
	    //renderer.setMargins(new int[] { 20, 30, 15, 0 });
	    XYSeriesRenderer newTicketRenderer = new XYSeriesRenderer();
	    newTicketRenderer.setColor(Color.BLUE);
	    renderer.addSeriesRenderer(newTicketRenderer);
	    XYSeriesRenderer fixedTicketRenderer = new XYSeriesRenderer();
	    fixedTicketRenderer.setColor(Color.GREEN);
	    renderer.addSeriesRenderer(fixedTicketRenderer);

	    renderer.setXLabels(0);
	    renderer.setYLabels(0);
	    renderer.setDisplayChartValues(false);
	    renderer.setShowGrid(false);
	    
	    GraphicalView chartView;
		/*if (chartView != null) {
			chartView.repaint();
		}
		else { */
			chartView = ChartFactory.getBubbleChartView(getActivity(), series, renderer);
		//}
		
	    LinearLayout layout = (LinearLayout) getActivity().findViewById(R.id.dashboard_chart_layout);
	    layout.removeAllViews();
	    layout.addView(chartView, new LayoutParams(960,
	              LayoutParams.FILL_PARENT));
	}
}
