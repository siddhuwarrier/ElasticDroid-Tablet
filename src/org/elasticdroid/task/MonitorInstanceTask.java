/**
 *  This file is part of ElasticDroid.
 *
 * ElasticDroid is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * ElasticDroid is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with ElasticDroid.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Authored by siddhu on 30 Dec 2010
 */
package org.elasticdroid.task;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.elasticdroid.intf.callback.RetrieveRegionCallbackIntf;
import org.elasticdroid.model.CloudWatchInput;
import org.elasticdroid.model.RegionReturnModel;
import org.elasticdroid.model.tables.AWSUserModel;

import android.content.Context;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClient;
import com.amazonaws.services.cloudwatch.model.Datapoint;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;

/**
 * Retrieve the metric required, and return an Object.
 * @author siddhu
 *
 * 30 Dec 2010
 */
public class MonitorInstanceTask extends GenericTask<Void, Void, Void, RetrieveRegionCallbackIntf>{

	/** Connection data */
	private HashMap<String, String> connectionData;
	/** The Cloudwatch input data to tell the CloudWatch API what data we want */
	private CloudWatchInput cloudWatchInput; 
	/** Logging tag */
	private static final String TAG = "org.elasticdroid.model.MonitorInstanceModel";

	private AWSUserModel awsUserTable;
	
	private GetMetricStatisticsResult result;
	private List<Datapoint> data;
	
	/**
	 * Constructor for type GenericActivity
	 * @param activity
	 */
	public MonitorInstanceTask(Context context, RetrieveRegionCallbackIntf callback, AWSUserModel userTable) {
		super(context, callback);
		this.awsUserTable = userTable;
	}
	
	/**
	 * Execute metric retrieval in background
	 */
	
	/** 
	 * Perform the actual work of retrieving the metrics
	 */
	public void retrieveMetrics() {
		//the cloudwatch client to use
		AmazonCloudWatchClient cloudWatchClient = null;
		//the request to send to cloudwatch
		GetMetricStatisticsRequest request;
		//the metric stats result.
		
		
		//create credentials using the BasicAWSCredentials class
		BasicAWSCredentials credentials = new BasicAWSCredentials(connectionData.get("accessKey"),
				connectionData.get("secretAccessKey"));
		//create a cloudwatch client
		try {
			cloudWatchClient = new AmazonCloudWatchClient(credentials);
		}
		catch(AmazonServiceException amazonServiceException) {
			//if an error response is returned by AmazonIdentityManagement indicating either a 
			//problem with the data in the request, or a server side issue.
			Log.e(this.getClass().getName(), "Exception:" + amazonServiceException.getMessage());
			//return amazonServiceException;
		}
		catch(AmazonClientException amazonClientException) { 
			//If any internal errors are encountered inside the client while attempting to make 
			//the request or handle the response. For example if a network connection is not
			//available. 
			Log.e(this.getClass().getName(), "Exception:" + amazonClientException.getMessage());
			//return amazonClientException;
		}
		
		//prepare request
		request = new GetMetricStatisticsRequest();
		request.setStartTime(new Date(cloudWatchInput.getStartTime()));
		request.setEndTime(new Date(cloudWatchInput.getEndTime()));
		request.setPeriod(cloudWatchInput.getPeriod());
		request.setMeasureName(cloudWatchInput.getMeasureName());
		request.setNamespace(cloudWatchInput.getNamespace());
		request.setStatistics(cloudWatchInput.getStatistics());
		
		//tell the cloudwatch client where to look!
		cloudWatchClient.setEndpoint("http://eu-west-1.ec2.amazonaws.com");
		
		//get the monitoring result!
		try {
			result = cloudWatchClient.getMetricStatistics(request);
		}
		catch(AmazonServiceException amazonServiceException) {
			//if an error response is returned by AmazonIdentityManagement indicating either a 
			//problem with the data in the request, or a server side issue.
			Log.e(this.getClass().getName(), "Exception:" + amazonServiceException.getMessage());
			//return amazonServiceException;
		}
		catch(AmazonClientException amazonClientException) { 
			//If any internal errors are encountered inside the client while attempting to make 
			//the request or handle the response. For example if a network connection is not 
			//available. 
			Log.e(this.getClass().getName(), "Exception:" + amazonClientException.getMessage());
			//return amazonClientException;
		}
		
		//get the data and print it out.
		data = result.getDatapoints();
		for (Datapoint datum : data) {
			Log.v(TAG, "Datum:" + datum.getAverage());
		}
		
		//sort the data in ascending order of timestamps
	}

	@Override
	protected void onPostExecute(Void ignore) {
		callback.dataRetrieved(data);
	}
	
	@Override
	protected Void doInBackground(Void... arg0) {
		// TODO Auto-generated method stub
		
		return null;
	}
}
