package org.elasticdroid.intf.callback;

import java.util.List;

import com.amazonaws.services.cloudwatch.model.Datapoint;

public interface CloudwatchMonitoringCallbackIntf extends GenericCallbackIntf {
	
	public void dataRetrieved(List<Datapoint> data);
}
