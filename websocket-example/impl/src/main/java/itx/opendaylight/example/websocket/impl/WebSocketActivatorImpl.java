/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.websocket.impl;

import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;

/**
 * Created by gergej on 2.12.2016.
 */
public class WebSocketActivatorImpl {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketActivatorImpl.class);

    private static final String SERVLET_NAME = "/websocket-example";

    private final WebSocketServletImpl webSocketServlet;

    public WebSocketActivatorImpl(WebSocketServletImpl webSocketServlet) {
        this.webSocketServlet = webSocketServlet;
    }

    public void onBind(HttpService httpService) {
        try {
            LOG.info("onBind: " + (webSocketServlet != null));
            httpService.registerServlet(SERVLET_NAME, webSocketServlet, null, null);
        } catch(ServletException | NamespaceException e) {
            LOG.error(e.getMessage(), e);
        }

    }

    public void onUnbind(HttpService httpService) {
        LOG.info("onUnbind");
        httpService.unregister(SERVLET_NAME);
    }

}
