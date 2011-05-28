package org.elasticdroid;

import org.elasticdroid.model.tables.AWSUserModel;

import android.app.Application;

public class ElasticDroidApp extends Application {
	
	private AWSUserModel awsUserModel;
	
    public AWSUserModel getAwsUserModel() {
		return awsUserModel;
	}

	public void setAwsUserModel(AWSUserModel awsUserModel) {
		this.awsUserModel = awsUserModel;
	}
}
