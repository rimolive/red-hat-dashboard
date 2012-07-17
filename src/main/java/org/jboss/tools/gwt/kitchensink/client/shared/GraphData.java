package org.jboss.tools.gwt.kitchensink.client.shared;

import java.util.Map;

import org.jboss.errai.common.client.api.annotations.Portable;

@Portable
public class GraphData {
	
	private Map<String, Integer> columnData;
	
	private Map<String, Integer> pieData;
	
	private Integer gaugeData;

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
