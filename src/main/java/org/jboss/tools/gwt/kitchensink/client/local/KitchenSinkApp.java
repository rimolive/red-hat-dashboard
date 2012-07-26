package org.jboss.tools.gwt.kitchensink.client.local;

import javax.inject.Inject;

import org.jboss.errai.ioc.client.api.AfterInitialization;
import org.jboss.errai.ioc.client.api.Caller;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
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
	
	private GraphDataController controller;

	@AfterInitialization
	public void createUI() {
		controller = new GraphDataController(memberService);
		Runnable onLoadCallback = new Runnable() {
			public void run() {
				final Panel panel = RootPanel.get("kitchensink");
				columnChart = new ColumnChart(createColumnData(), createColumnOptions());
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
				
				Timer t = new Timer() {
					public void run() {
						columnChart.draw(createColumnData(), createColumnOptions());
						gauge.draw(createTable(), createOptions());
						pieChart.draw(createPieData(), createPieOptions());
					}
				};

				t.scheduleRepeating(1000);
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
		GWT.log("Collecting Pie Chart data...");
		return getDataController().generatePieChartData();
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
		GWT.log("Collecting Column Chart data...");
		return getDataController().generateColumnChartData();
	}

	protected AbstractDataTable createTable() {
		GWT.log("Collecting Transaction Rate Metrics...");
		return getDataController().generateTransactionRateData();
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
