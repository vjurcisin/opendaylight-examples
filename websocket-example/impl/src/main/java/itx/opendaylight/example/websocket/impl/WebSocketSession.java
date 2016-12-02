/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.example.websocket.impl;

import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by gergej on 2.12.2016.
 */
public class WebSocketSession implements WebSocket.OnTextMessage, WebSocket.OnBinaryMessage {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketSession.class);

    private Connection connection;

    @Override
    public void onMessage(byte[] bytes, int i, int i1) {
        LOG.info("onMessage");
    }

    @Override
    public void onMessage(String s) {
        LOG.info("onMessage: " + s);
        try {
            connection.sendMessage("Echo: " + s);
        } catch (IOException e) {
            LOG.error("onMessage Failed");
        }
    }

    @Override
    public void onOpen(Connection connection) {
        LOG.info("onOpen");
        this.connection = connection;
    }

    @Override
    public void onClose(int i, String s) {
        LOG.info("onClose");
    }

}
