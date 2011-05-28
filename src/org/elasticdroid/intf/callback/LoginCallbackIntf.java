package org.elasticdroid.intf.callback;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

public interface LoginCallbackIntf extends GenericCallbackIntf {
	
	public void loginSuccess();
}
