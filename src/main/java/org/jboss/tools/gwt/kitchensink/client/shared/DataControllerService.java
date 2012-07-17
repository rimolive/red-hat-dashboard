package org.jboss.tools.gwt.kitchensink.client.shared;

import java.util.Map;

import org.jboss.errai.bus.server.annotations.Remote;

@Remote
public interface DataControllerService {
	
	Integer getTxRate();
	
	Map<String,Integer> getTransactionVolume();
	
	Map<String,Integer> getTransactionsStatus();

}
