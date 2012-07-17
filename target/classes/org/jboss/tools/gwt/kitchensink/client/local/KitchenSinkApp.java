package org.jboss.tools.gwt.kitchensink.client.local;

import javax.inject.Inject;

import org.eclipse.jdt.internal.core.util.MementoTokenizer;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.Caller;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService;
import org.jboss.tools.gwt.kitchensink.client.shared.GraphData;

import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.ColumnChart;
import com.google.gwt.visualization.client.visualizations.Gauge;
import com.google.gwt.visualization.client.visualizations.Gauge.Options;
import com.google.gwt.visualization.client.visualizations.PieChart;

@EntryPoint
public class KitchenSinkApp {

	@UiField
	Gauge gauge;

	@UiField
	ColumnChart columnChart;

	@UiField
	PieChart pieChart;

	@Inject
	private Caller<DataControllerService> memberService;
	
	private GraphData graphData;
	
	private GraphDataController controller;

	@AfterInitialization
	public void createUI() {
		controller = new GraphDataController(memberService);
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				Panel panel = RootPanel.get("kitchensink");

				columnChart = new ColumnChart(createColumnData(),
						createColumnOptions());
				gauge = new Gauge(createTable(), createOptions());
				pieChart = new PieChart(createPieData(), createPieOptions());

				Grid g = new Grid(2, 1);
				g.setWidth("865px");
				Grid c = new Grid(1, 2);
				c.setCellSpacing(10);
				c.setWidget(0, 0, pieChart);
				c.setWidget(0, 1, gauge);
				g.setWidget(0, 0, c);
				g.setWidget(1, 0, columnChart);

				panel.add(g);
			}
		};

		VisualizationUtils.loadVisualizationApi(onLoadCallback, Gauge.PACKAGE,
				ColumnChart.PACKAGE, PieChart.PACKAGE);
	}

	protected com.google.gwt.visualization.client.visualizations.PieChart.Options createPieOptions() {
		com.google.gwt.visualization.client.visualizations.PieChart.Options options = com.google.gwt.visualization.client.visualizations.PieChart.Options
				.create();
		options.set3D(true);
		options.setHeight(400);
		options.setWidth(400);
		return options;
	}

	protected AbstractDataTable createPieData() {
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
		data.addRows(map.size());
		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
			String key = it.next();
			data.setValue(0, 0, key);
			data.setValue(0, 1, map.get(key));
		}
		return data;
	}

	private GraphDataController getDataController() {
		return controller;
	}

	protected com.google.gwt.visualization.client.visualizations.ColumnChart.Options createColumnOptions() {
		com.google.gwt.visualization.client.visualizations.ColumnChart.Options options = com.google.gwt.visualization.client.visualizations.ColumnChart.Options
				.create();
		options.set3D(true);
		options.setHeight(400);
		return options;
	}

	protected AbstractDataTable createColumnData() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.STRING, "Bandeira");
		data.addColumn(ColumnType.NUMBER, "Volume de Transacoes");
		System.out.println("Requesting member list...");
//		memberService.call(new RemoteCallback<Map<String, Integer>>() {
//
//			@Override
//			public void callback(Map<String, Integer> response) {
//				Window.alert("response content: " + response);
//				map = response;
//			}
//		}).getTransactionVolume();
//		data.addRows(map.size());
//		for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
//			String key = it.next();
//			data.setValue(0, 0, key);
//			data.setValue(0, 1, map.get(key));
//		}

		return data;
	}

	protected AbstractDataTable createTable() {
		DataTable data = DataTable.create();
		data.addColumn(ColumnType.NUMBER, "Tx/minuto");
		data.addRow();
		memberService.call(new RemoteCallback<Integer>() {

			@Override
			public void callback(Integer response) {
				Window.alert("Response Data: " + response);
				graphData.setGaugeData(response.intValue());
			}
		}).getTxRate();
		data.setValue(0, 0, graphData.getGaugeData().intValue());
		return data;
	}

	protected Options createOptions() {
		Options options = Options.create();
		options.setGaugeRange(0, 100);
		options.setGreenRange(0, 30);
		options.setYellowRange(70, 80);
		options.setRedRange(81, 100);
		options.setHeight(300);
		options.setWidth(300);
		return options;
	}

}
