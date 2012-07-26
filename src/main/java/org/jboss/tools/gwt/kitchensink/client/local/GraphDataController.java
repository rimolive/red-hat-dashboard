package org.jboss.tools.gwt.kitchensink.client.local;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.Caller;
import org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService;

import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;

public class GraphDataController {

	private final Caller<DataControllerService> controller;

	private Map<String, Integer> columnData = new HashMap<String, Integer>();
	
	private Map<String, Integer> pieData = new HashMap<String, Integer>();
	
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
				columnData.clear();
				columnData.putAll(response);
			}
		}).getTransactionVolume();
		data.addRows(columnData.size());
		int i = 0;
		for (Iterator<String> it = columnData.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			data.setValue(i, 0, key);
			data.setValue(i, 1, columnData.get(key));
			i++;
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
		controller.call(new RemoteCallback<Map<String, Integer>>() {
			@Override
			public void callback(Map<String, Integer> response) {
				pieData.clear();
				pieData.putAll(response);
			}
		}).getTransactionsStatus();
		data.addRows(pieData.size());
		int i = 0;
		for (Iterator<String> it = pieData.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			data.setValue(i, 0, key);
			data.setValue(i, 1, pieData.get(key));
			i++;
		}
		return data;
	}

}
