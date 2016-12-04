/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.webui.impl;

import itx.opendaylight.examples.akka.demo.eventbus.api.TopicManager;
import org.eclipse.jetty.websocket.WebSocket;
import org.eclipse.jetty.websocket.WebSocketServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gergej on 4.12.2016.
 */
public class WebSocketServletImpl extends WebSocketServlet {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketServletImpl.class);

    private TopicManager topicManager;

    public WebSocketServletImpl(TopicManager topicManager) {
        this.topicManager = topicManager;
    }

    @Override
    public WebSocket doWebSocketConnect(HttpServletRequest httpServletRequest, String s) {
        LOG.info("doWebSocketConnect");
        WebSocketSession session = new WebSocketSession(topicManager);
        return session;
    }

}
