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
 * Authored by Siddhu Warrier on 8 Dec 2010
 */
package org.elasticdroid.task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticdroid.intf.callback.RetrieveRegionCallbackIntf;
import org.elasticdroid.model.RegionReturnModel;
import org.elasticdroid.model.tables.AWSUserModel;
import org.elasticdroid.utils.AWSResultConstants;

import android.content.Context;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.Region;


/**
 * @author Siddhu Warrier
 *
 * 8 Dec 2010
 */
public class RetrieveRegionTask extends GenericTask<Void, Void, RegionReturnModel, RetrieveRegionCallbackIntf> {

	private AWSUserModel awsUserTable;
	
	private static final String TAG = RetrieveRegionTask.class.getName();
	
	public RetrieveRegionTask(Context context, RetrieveRegionCallbackIntf callback, AWSUserModel userTable) {
		super(context, callback);
		this.awsUserTable = userTable;
	}

	@Override
	protected RegionReturnModel doInBackground(Void... ignore) {
		List<Region> regions;//data from AWS.
		RegionReturnModel returnModel = new RegionReturnModel(); //data to onPostExecute
		
		Log.d(TAG, "Retrieve region data...");
		
		if (awsUserTable.isNull()) {
			returnModel.setResultConstants(AWSResultConstants.INVALID_INPUT);
			return returnModel;
		}
		
		BasicAWSCredentials credentials = new BasicAWSCredentials(
				awsUserTable.getAwsAccessKey(),
				awsUserTable.getAwsSecretAccessKey());
		AmazonEC2Client amazonEC2Client = new AmazonEC2Client(credentials);
		
		try {
			regions = amazonEC2Client.describeRegions().getRegions();
			
			returnModel.setResultConstants(AWSResultConstants.OPERATION_SUCCESS);
			returnModel.setRegions(regions);
		}
		catch(AmazonServiceException amazonServiceException) {
			//this is an unchecked exception subclassed from RuntimeException. So throw it manually
			Log.v(this.getClass().getName(), "Caught ServiceException.");
			
			returnModel.setResultConstants(AWSResultConstants.AMAZON_SERVICE_EXCEPTION);
			returnModel.setAwsServiceException(amazonServiceException);
		}
		catch (AmazonClientException amazonClientException) {
			//this is an unchecked exception subclassed from RuntimeException. So throw it manually
			Log.v(this.getClass().getName(), "Caught ClientException.");
			returnModel.setResultConstants(AWSResultConstants.AMAZON_CLIENT_EXCEPTION);
			returnModel.setAwsClientException(amazonClientException);
		}
		
		return returnModel;
	}
	
	@Override
	protected void onPostExecute(RegionReturnModel returnModel) {
		
		switch(returnModel.getResultConstants()) {
		case OPERATION_SUCCESS:
			Map<String, String[]> regionData = new HashMap<String, String[]>();
			String regionApiName, regionHumanName;
			
			for (Region region : returnModel.getRegions()) {
				
				regionApiName = region.getRegionName();
				regionHumanName = regionApiName.replace("-", " ");
				regionHumanName = regionApiName.replace("-\\d", ""); //remove thet railing number
				
				Log.d(TAG, "Region Human Name: "  + regionHumanName);
				regionData.put(regionApiName, new String[]{regionHumanName, region.getEndpoint()});
			}
			
			callback.regionsRetrieved(regionData);
			break;
			
		case INVALID_INPUT:
			//TODO handle
			break;
			
		case AMAZON_CLIENT_EXCEPTION:
			callback.awsClientException(returnModel.getAwsClientException());
			break;
			
		case AMAZON_SERVICE_EXCEPTION:
			callback.awsServerException(returnModel.getAwsServiceException());
			break;
		}
	}
}
