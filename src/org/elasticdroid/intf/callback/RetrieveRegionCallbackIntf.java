package org.elasticdroid.intf.callback;

import java.util.Map;

public interface RetrieveRegionCallbackIntf extends GenericCallbackIntf {
	
	public void regionsRetrieved(Map<String, String> regions);
}
