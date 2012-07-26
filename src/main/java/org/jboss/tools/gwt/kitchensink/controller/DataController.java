package org.jboss.tools.gwt.kitchensink.controller;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService;

@ApplicationScoped
@Service
public class DataController implements DataControllerService {
	
	private Event<Map<String, Integer>> event;

	@Override
	public Integer getTxRate() {
		return (int)(Math.random() * 100);
	}

	@Override
	public Map<String,Integer> getTransactionVolume() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("Visa", 0);
		data.put("Amex", 0);
		data.put("Master", 0);
		data.put("Diners", 0);
		return data;
	}

	@Override
	public Map<String,Integer> getTransactionsStatus() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("Cancelado", 0);
		data.put("Aprovado", 0);
		data.put("Negado", 0);
		return data;
	}
	
	
	
}
