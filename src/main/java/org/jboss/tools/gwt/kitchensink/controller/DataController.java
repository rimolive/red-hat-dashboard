package org.jboss.tools.gwt.kitchensink.controller;

import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.errai.bus.server.annotations.Service;
import org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService;

@ApplicationScoped
@Service
public class DataController implements DataControllerService {
	
	@Override
	public Integer getTxRate() {
		return (int)(Math.random() * 100);
	}

	@Override
	public Map<String,Integer> getTransactionVolume() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("Visa", (int)(Math.random() * 120));
		data.put("Amex", (int)(Math.random() * 150));
		data.put("Master", (int)(Math.random() * 100));
		data.put("Diners", (int)(Math.random() * 180));
		return data;
	}

	@Override
	public Map<String,Integer> getTransactionsStatus() {
		Map<String, Integer> data = new HashMap<String, Integer>();
		data.put("Cancelado", (int)(Math.random() * 120));
		data.put("Aprovado", (int)(Math.random() * 120));
		data.put("Negado", (int)(Math.random() * 120));
		return data;
	}
	
	
	
}
