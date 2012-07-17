package org.jboss.tools.gwt.kitchensink.client.local;

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

	private Map<String, Integer> map;

	public GraphDataController(Caller<DataControllerService> controller) {
		this.controller = controller;
	}

	public AbstractDataTable generateColumnChartData() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Bandeira");
		data.addColumn(ColumnType.NUMBER, "Volume de Transacoes");
		System.out.println("Requesting member list...");
		map = controller.call(new RemoteCallback<Map<String, Integer>>() {

			@Override
			public void callback(Map<String, Integer> response) {
				System.out.println("Got result!");
			}
		}, new ErrorCallback() {
			@Override
			public boolean error(Message message, Throwable throwable) {
				throwable.printStackTrace();
				System.out.println("Failed to retrieve list of members: " + throwable.getMessage());
				return false;
			}
		}).getTransactionVolume();
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
		data.setValue(0, 0, (int) (20 + (Math.random() * 10)));
		return data;
	}

	public AbstractDataTable generatePieChartData() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Satistacao");
		data.addColumn(ColumnType.NUMBER, "Porcentagem");
		GWT.log("Requesting member list...");
		controller.call(new RemoteCallback<Map<String, Integer>>() {

			@Override
			public void callback(Map<String, Integer> response) {
				GWT.log("Got member list. Size: " + response.size());
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
