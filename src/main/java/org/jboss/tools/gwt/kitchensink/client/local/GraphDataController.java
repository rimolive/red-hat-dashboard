package org.jboss.tools.gwt.kitchensink.client.local;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.Message;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;
import org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;

public class GraphDataController {

	private final Caller<DataControllerService> controller;

	private Map<String, Integer> map = new HashMap<String, Integer>();
	
	private Integer number = new Integer(0);

	public GraphDataController(Caller<DataControllerService> controller) {
		this.controller = controller;
	}

	public AbstractDataTable generateColumnChartData() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Bandeira");
		data.addColumn(ColumnType.NUMBER, "Volume de Transacoes");
		controller.call(new RemoteCallback<Map<String, Integer>>() {

			@Override
			public void callback(Map<String, Integer> response) {
				GWT.log("Response size: " + response.size());
				map.clear();
				map.putAll(response);
				GWT.log("After map.add() size: " + map.size());
			}
		}, new ErrorCallback() {
			@Override
			public boolean error(Message message, Throwable throwable) {
				throwable.printStackTrace();
				GWT.log("Failed to retrieve list of members: " + throwable.getMessage());
				return false;
			}
		}).getTransactionVolume();
		GWT.log("Column Chart size: " + map.size());
		data.addRows(map.size());
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			data.setValue(0, 0, key);
			data.setValue(0, 1, map.get(key));
		}

		return data;
	}

	public AbstractDataTable generateTransactionRateData() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.NUMBER, "Tx/minuto");
		data.addRow();
		controller.call(new RemoteCallback<Integer>() {

			@Override
			public void callback(Integer response) {
				number = new Integer(response.intValue());
			}
		}).getTxRate();
		data.setValue(0, 0, number.intValue());
		return data;
	}

	public AbstractDataTable generatePieChartData() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Satistacao");
		data.addColumn(ColumnType.NUMBER, "Porcentagem");
//		GWT.log("Requesting member list...");
		controller.call(new RemoteCallback<Map<String, Integer>>() {

			@Override
			public void callback(Map<String, Integer> response) {
//				GWT.log("Got member list. Size: " + response.size());
				map = response;
			}
		}).getTransactionsStatus();
		// data.addRows(map.size());
		// for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
		// String key = it.next();
		// data.setValue(0, 0, key);
		// data.setValue(0, 1, map.get(key));
		// }
		return data;
	}

}
