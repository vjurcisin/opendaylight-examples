/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.webui.impl;

import akka.actor.UntypedActor;
import itx.opendaylight.examples.akka.demo.eventbus.api.TopicManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 4.12.2016.
 */
public class WebSocketActor extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(WebSocketActor.class);

    private TopicManager topicManager;
    private WebSocketSession wsSession;

    public WebSocketActor(TopicManager topicManager, WebSocketSession wsSession) {
        LOG.info("init " + wsSession.getId());
        this.topicManager = topicManager;
        this.wsSession = wsSession;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        LOG.info("onReceive");

    }

}
