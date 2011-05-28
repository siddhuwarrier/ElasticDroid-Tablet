package org.elasticdroid.model;

import org.elasticdroid.utils.AWSResultConstants;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

public class LoginReturnModel {
	private AWSResultConstants resultConstants;
	private AmazonClientException awsClientException;
	private AmazonServiceException awsServiceException;
	public AWSResultConstants getResultConstants() {
		return resultConstants;
	}
	public void setResultConstants(AWSResultConstants resultConstants) {
		this.resultConstants = resultConstants;
	}
	public AmazonClientException getAwsClientException() {
		return awsClientException;
	}
	public void setAwsClientException(AmazonClientException awsClientException) {
		this.awsClientException = awsClientException;
	}
	public AmazonServiceException getAwsServiceException() {
		return awsServiceException;
	}
	public void setAwsServiceException(AmazonServiceException awsServiceException) {
		this.awsServiceException = awsServiceException;
	}
}
