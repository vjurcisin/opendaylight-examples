/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.websocket.impl;

import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gergej on 2.12.2016.
 */
public class WebSocketServletImpl extends WebSocketServlet {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServletImpl.class);

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String s) {
        LOG.info("doWebSocketConnect");
        WebSocketSession session = new WebSocketSession();
        return session;
    }

}
