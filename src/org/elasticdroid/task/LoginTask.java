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
 * Authored by Siddhu Warrier on 1 Nov 2010
 */
package org.elasticdroid.task;

import org.elasticdroid.db.tables.AWSUserTable;
import org.elasticdroid.intf.callback.LoginCallbackIntf;
import org.elasticdroid.model.LoginReturnModel;
import org.elasticdroid.utils.AWSResultConstants;

import android.content.Context;
import android.util.Log;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.identitymanagement.AmazonIdentityManagementClient;

/**
 * This class is the model class for the Login window. It performs the following actions:
 * a) Verifies credentials by connecting to AWS.
 * b) Stores valid credentials in database.
 * c) Notifies observers (presently the Login activity alone) that authentication is valid (or not).
 * d) Can be used to retrieve saved credentials from SQLite DB.
 * @author Siddhu Warrier
 *
 * 1 Nov 2010
 */
public class LoginTask extends GenericTask<Void, Void, LoginReturnModel, LoginCallbackIntf> {
	
	private AWSUserTable awsUserTable;
	
	
	/**
	 * To call super Constructor alone. To read what this constructor
	 * does, please refer to the superclass documentation.
	 */
	public LoginTask(Context context, LoginCallbackIntf callback, AWSUserTable awsUserTable) {
		super(context, callback);
		
		this.awsUserTable = awsUserTable;
	}
	
	/** 
	 * Check AWS credentials, and save to DB if valid.
	 * 
	 * When this method finishes, the 
	 * {@link org.elasticdroid.task.GenericTask#onPostExecute(Object)} is called, which
	 * notifies the view.
	 * 
	 * This method, inherited from Android AsyncTask is automagically run in a separate background
	 *  thread.
	 */
	@Override
	protected LoginReturnModel doInBackground(Void... params) {
		return performLogin();
	}
	
	public LoginReturnModel performLogin() {
		LoginReturnModel returnModel = new LoginReturnModel();
		returnModel.setResultConstants(AWSResultConstants.LOGIN_SUCCESS);
		
		if (awsUserTable.isNull()) {
			returnModel.setResultConstants(AWSResultConstants.INVALID_INPUT);
			return returnModel;
		}
		
		//create credentials using the BasicAWSCredentials class
		BasicAWSCredentials credentials = new BasicAWSCredentials(
				awsUserTable.getAwsAccessKey(),
				awsUserTable.getAwsSecretAccessKey()
				);
		//create an IAM client
		AmazonIdentityManagementClient idManagementClient = new AmazonIdentityManagementClient
			(credentials);
		
		try {
			idManagementClient.getUser().getUser();//ensure the user ID is 
			//matched to the access and secret access keys
		}
		catch(AmazonServiceException amazonServiceException) {
			amazonServiceException.printStackTrace();
			//if an error response is returned by AmazonIdentityManagement indicating either a 
			//problem with the data in the request, or a server side issue.
			Log.e(this.getClass().getName(), "Exception:" + amazonServiceException.getMessage());
			
			returnModel.setAwsServiceException(amazonServiceException);
			returnModel.setResultConstants(AWSResultConstants.AMAZON_SERVICE_EXCEPTION);
		}
		catch(AmazonClientException amazonClientException) { 
			amazonClientException.printStackTrace();
			//If any internal errors are encountered inside the client while attempting to make 
			//the request or handle the response. For example if a network connection is not available. 
			Log.e(this.getClass().getName(), "Exception:" + amazonClientException.getMessage());
			
			returnModel.setAwsClientException(amazonClientException);
			returnModel.setResultConstants(AWSResultConstants.AMAZON_CLIENT_EXCEPTION);
		}
		
		return returnModel;
	}
	
	/**
	 * Called after do in background
	 */
	@Override
	protected void onPostExecute(LoginReturnModel returnModel) {
		super.onPostExecute(returnModel);
		
		if (callback == null) {
			return;
		}
		
		switch (returnModel.getResultConstants()) {
		
		case LOGIN_SUCCESS:
			callback.loginSuccess();
			break;
			
		case AMAZON_SERVICE_EXCEPTION:
			callback.awsServerException(returnModel.getAwsServiceException());
			break;
		
		case AMAZON_CLIENT_EXCEPTION:
			callback.awsClientException(returnModel.getAwsClientException());
			break;
		}
	}
}