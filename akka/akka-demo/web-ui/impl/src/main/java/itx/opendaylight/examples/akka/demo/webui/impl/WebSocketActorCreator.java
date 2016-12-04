/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.webui.impl;

import akka.japi.Creator;
import itx.opendaylight.examples.akka.demo.eventbus.api.TopicManager;

/**
 * Created by gergej on 4.12.2016.
 */
public class WebSocketActorCreator implements Creator<WebSocketActor> {

    private TopicManager topicManager;
    private WebSocketSession wsSession;

    public WebSocketActorCreator(TopicManager topicManager, WebSocketSession wsSession) {
        this.topicManager = topicManager;
        this.wsSession = wsSession;
    }

    @Override
    public WebSocketActor create() throws Exception {
        return new WebSocketActor(topicManager, wsSession);
    }

}
