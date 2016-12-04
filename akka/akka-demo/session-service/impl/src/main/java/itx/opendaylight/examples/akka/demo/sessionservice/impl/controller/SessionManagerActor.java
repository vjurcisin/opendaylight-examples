/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.sessionservice.impl.controller;

import akka.actor.UntypedActor;
import itx.opendaylight.examples.akka.demo.eventbus.api.MessageBase;
import itx.opendaylight.examples.akka.demo.eventbus.api.TopicManager;
import itx.opendaylight.examples.akka.demo.sessionservice.api.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 4.12.2016.
 */
public class SessionManagerActor extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(SessionManagerActor.class);

    private SessionManager sessionManager;
    private TopicManager topicManager;

    public SessionManagerActor(SessionManager sessionManager, TopicManager topicManager) {
        LOG.info("init ...");
        this.sessionManager = sessionManager;
        this.topicManager = topicManager;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof  MessageBase) {
            MessageBase message = (MessageBase) o;
            LOG.info("onReceive: " + message.getType());
            switch (message.getType()) {

            }
        } else {
            LOG.info("onReceive: unsupported message");
        }
    }

}
