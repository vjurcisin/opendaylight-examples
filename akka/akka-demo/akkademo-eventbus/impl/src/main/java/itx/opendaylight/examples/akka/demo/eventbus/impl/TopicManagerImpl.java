/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.eventbus.impl;

import akka.actor.UntypedActor;
import akka.japi.Creator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import akka.actor.ActorRef;
import akka.actor.Props;
import itx.opendaylight.examples.akka.demo.eventbus.api.MessageBase;
import itx.opendaylight.examples.akka.demo.eventbus.api.TopicManager;
import org.opendaylight.controller.cluster.ActorSystemProvider;

/**
 * @author gergej
 */
public class TopicManagerImpl implements TopicManager {

    private static final Logger LOG = LoggerFactory.getLogger(TopicManagerImpl.class);

    private ActorSystemProvider actorSystemProvider;
    private LookupBusImpl lookupBus;

    public void init() {
        LOG.info("init ...");
        lookupBus = new LookupBusImpl();
    }

    public void destroy() {
        LOG.info("destroy");
    }

    @Override
    public ActorRef subscribe(Creator<? extends UntypedActor> actorCreator, String topicId, String actorName) {
        LOG.info("subscribe: " + topicId);
        ActorRef actorRef = actorSystemProvider.getActorSystem().actorOf(Props.create(actorCreator), actorName);
        lookupBus.subscribe(actorRef, topicId);
        return actorRef;
    }

    @Override
    public void unsubscribe(ActorRef actorRef) {
        LOG.info("unsubscribe");
        lookupBus.unsubscribe(actorRef);
    }

    @Override
    public void publish(MessageBase message) {
        lookupBus.publish(message);
    }

    public void setActorSystemProvider(ActorSystemProvider actorSystemProvider) {
        this.actorSystemProvider = actorSystemProvider;
    }

}
