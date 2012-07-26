package org.jboss.tools.gwt.kitchensink.client.shared;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class GraphData implements Serializable {
	
	private Map<String, Integer> columnData = new HashMap<String, Integer>();
	
	private Map<String, Integer> pieData = new HashMap<String, Integer>();
	
	private Integer gaugeData = 0;

	public Map<String, Integer> getColumnData() {
		return columnData;
	}

	public void setColumnData(Map<String, Integer> columnData) {
		this.columnData = columnData;
	}

	public Map<String, Integer> getPieData() {
		return pieData;
	}

	public void setPieData(Map<String, Integer> pieData) {
		this.pieData = pieData;
	}

	public Integer getGaugeData() {
		return gaugeData;
	}

	public void setGaugeData(Integer gaugeData) {
		this.gaugeData = gaugeData;
	}

}
