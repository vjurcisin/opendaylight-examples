/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.sessionservice.impl.controller;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import itx.opendaylight.examples.akka.demo.eventbus.api.TopicManager;
import itx.opendaylight.examples.akka.demo.sessionservice.api.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 4.12.2016.
 */
public class SessionManagerController {

    private static final Logger LOG = LoggerFactory.getLogger(SessionManagerController.class);

    private SessionManager sessionManager;
    private TopicManager topicManager;
    private ActorRef smActor;

    public SessionManagerController(SessionManager sessionManager, TopicManager topicManager) {
        LOG.info("SessionManagerController init ...");
        this.sessionManager = sessionManager;
        this.topicManager = topicManager;
    }

    public void init() {
        LOG.info("init ...");
        SessionManagerActorCreator creator = new SessionManagerActorCreator(sessionManager, topicManager);
        smActor = topicManager.subscribe(creator, SessionManager.topic, "sessionManager");
    }

    public void destroy() {
        LOG.info("destroy ...");
        topicManager.unsubscribe(smActor);
    }

}
