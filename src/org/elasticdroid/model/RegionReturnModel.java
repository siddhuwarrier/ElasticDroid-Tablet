package org.elasticdroid.model;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.services.ec2.model.Region;

public class RegionReturnModel extends BaseReturnModel {
	List<Region> regions;
	
	public RegionReturnModel() {
		regions = new ArrayList<Region>();
	}
	
	public List<Region> getRegions() {
		return regions;
	}
	
	public void setRegions(List<Region> regions) {
		this.regions = regions;
	}
}
