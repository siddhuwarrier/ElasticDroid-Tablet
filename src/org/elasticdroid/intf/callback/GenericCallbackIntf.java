/*******************************************************************************
 * The file GenericCallback.java is part of ChipsAway.
 *  
 * Copyright (c) Cloudreach Limited 2011. All rights reserved. 
 *  
 *  Authored by Siddhu Warrier and Rodolfo Cartas Ayala.
 ******************************************************************************/
package org.elasticdroid.intf.callback;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

/**
 * Generic interface with methods that should be implemented by all callback objects.
 * 
 * This interface is presently empty.
 * 
 * @author siddhuwarrier
 *
 */
public interface GenericCallbackIntf {
	/**
	 * Cleans up model after execution. Typically sets model object to null
	 */
	public void cleanUpAfterTaskExecution();
	
	public void awsClientException(AmazonClientException exception);
	
	public void awsServerException(AmazonServiceException exception);
}
