/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.webui.impl;

import akka.actor.ActorRef;
import itx.opendaylight.examples.akka.demo.eventbus.api.TopicManager;
import itx.opendaylight.examples.akka.demo.sessionservice.api.SessionManager;
import org.eclipse.jetty.websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by gergej on 4.12.2016.
 */
public class WebSocketSession implements WebSocket.OnTextMessage, WebSocket.OnBinaryMessage {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketSession.class);

    private Connection connection;
    private String id;
    private TopicManager topicManager;
    private ActorRef actorRef;

    public WebSocketSession(TopicManager topicManager) {
        id = UUID.randomUUID().toString();
        LOG.info("WebSocketSession: " + id);
        this.topicManager = topicManager;
        WebSocketActorCreator actorCreator = new WebSocketActorCreator(topicManager, this);
        actorRef = topicManager.subscribe(actorCreator, SessionManager.topic + TopicManager.TOPIC_SEPARATOR + id, "wsclient-" + id);
    }

    @Override
    public void onMessage(byte[] bytes, int i, int i1) {
        LOG.info("onMessage");
    }

    @Override
    public void onMessage(String s) {
        LOG.info("onMessage: " + s);
    }

    @Override
    public void onOpen(Connection connection) {
        LOG.info("onOpen");
        this.connection = connection;
    }

    @Override
    public void onClose(int i, String s) {
        LOG.info("onClose");
        topicManager.unsubscribe(actorRef);
    }

    public void sendMessage(String message) {
        try {
            connection.sendMessage(message);
        } catch (IOException e) {
            LOG.info("IOException");
        }
    }

    public String getId() {
        return id;
    }

}
