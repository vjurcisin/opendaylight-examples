/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.pubsub;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import org.opendaylight.controller.cluster.ActorSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by gergej on 8.12.2016.
 */
public class ClusterPubSub {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterPubSub.class);
    public static final String DEFAULT_TOPIC = "defaultTopic";

    private ActorSystemProvider actorSystemProvider;
    private ActorRef actorRef;

    public ClusterPubSub(ActorSystemProvider actorSystemProvider) {
        LOG.info("ClusterPubSub");
        this.actorSystemProvider = actorSystemProvider;
    }

    public void init() {
        LOG.info("init");
        String actorName = "subscriber-" + UUID.randomUUID().toString();
        ActorSystem actorSystem = this.actorSystemProvider.getActorSystem();
        actorRef = actorSystem.actorOf(Props.create(new SubscriberCreator(DEFAULT_TOPIC)), actorName);
        LOG.info("Actor created: " + actorName);
    }

    public void destroy() {
        LOG.info("destroy");
    }

}
