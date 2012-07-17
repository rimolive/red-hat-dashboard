package org.jboss.errai.bus.client.framework;

import java.lang.annotation.Annotation;
import java.util.Map;
import org.jboss.errai.bus.client.api.ErrorCallback;
import org.jboss.errai.bus.client.api.RemoteCallback;
import org.jboss.errai.bus.client.api.base.MessageBuilder;
import org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService;

public class RpcProxyLoaderImpl implements RpcProxyLoader {
  public void loadProxies(final MessageBus bus) {
    class DataControllerServiceImpl implements DataControllerService, RPCStub {
      private RemoteCallback remoteCallback;
      private ErrorCallback errorCallback;
      private Annotation[] qualifiers;
      public void setErrorCallback(ErrorCallback callback) {
        errorCallback = callback;
      }

      public void setRemoteCallback(RemoteCallback callback) {
        remoteCallback = callback;
      }

      public void setQualifiers(Annotation[] quals) {
        qualifiers = quals;
      }

      public Integer getTxRate() {
        if (errorCallback == null) {
          MessageBuilder.createCall().call("org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService").endpoint("getTxRate:", qualifiers, new Object[] { }).respondTo(Integer.class, remoteCallback).defaultErrorHandling().sendNowWith(bus);
        } else {
          MessageBuilder.createCall().call("org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService").endpoint("getTxRate:", qualifiers, new Object[] { }).respondTo(Integer.class, remoteCallback).errorsHandledBy(errorCallback).sendNowWith(bus);
        }
        return 0;
      }

      public Map getTransactionVolume() {
        if (errorCallback == null) {
          MessageBuilder.createCall().call("org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService").endpoint("getTransactionVolume:", qualifiers, new Object[] { }).respondTo(Map.class, remoteCallback).defaultErrorHandling().sendNowWith(bus);
        } else {
          MessageBuilder.createCall().call("org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService").endpoint("getTransactionVolume:", qualifiers, new Object[] { }).respondTo(Map.class, remoteCallback).errorsHandledBy(errorCallback).sendNowWith(bus);
        }
        return null;
      }

      public Map getTransactionsStatus() {
        if (errorCallback == null) {
          MessageBuilder.createCall().call("org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService").endpoint("getTransactionsStatus:", qualifiers, new Object[] { }).respondTo(Map.class, remoteCallback).defaultErrorHandling().sendNowWith(bus);
        } else {
          MessageBuilder.createCall().call("org.jboss.tools.gwt.kitchensink.client.shared.DataControllerService").endpoint("getTransactionsStatus:", qualifiers, new Object[] { }).respondTo(Map.class, remoteCallback).errorsHandledBy(errorCallback).sendNowWith(bus);
        }
        return null;
      }
    }
    RemoteServiceProxyFactory.addRemoteProxy(DataControllerService.class, new ProxyProvider() {
      public Object getProxy() {
        return new DataControllerServiceImpl();
      }
    });
  }
}