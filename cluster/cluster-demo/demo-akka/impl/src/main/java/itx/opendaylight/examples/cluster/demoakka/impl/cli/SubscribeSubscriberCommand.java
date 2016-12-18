/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.cli;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import itx.opendaylight.examples.cluster.demoakka.impl.pubsub.SubscriberCreator;
import itx.opendaylight.examples.cluster.demoakka.impl.pubsub.SubscriberManager;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opendaylight.controller.cluster.ActorSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by gergej on 8.12.2016.
 */
@Command(scope = "pubsub", name = "subscribe", description = "subscribe new actor to topic")
public class SubscribeSubscriberCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(PublishCommand.class);

    private ActorSystemProvider actorSystemProvider;
    private SubscriberManager subscriberManager;

    @Argument(index = 0, name = "topicName",
            description = "create actor and subscribe it to",
            required = true,
            multiValued = false)
    private String topicName;

    @Override
    protected Object doExecute() throws Exception {
        LOG.info("subscribe to " + topicName);
        try {
            String actorName = "subscriber-" + UUID.randomUUID().toString();
            ActorSystem actorSystem = this.actorSystemProvider.getActorSystem();
            ActorRef actorRef = actorSystem.actorOf(Props.create(new SubscriberCreator(topicName)), actorName);
            subscriberManager.subscribe(actorRef, actorName, topicName);
            LOG.info("Actor created: " + actorName);
            return "Subscribed to " + topicName;
        } catch (Exception e) {
            LOG.error("ERROR: ", e);
            return "ERROR";
        }
    }

    public void setActorSystemProvider(ActorSystemProvider actorSystemProvider) {
        this.actorSystemProvider = actorSystemProvider;
    }

    public void setSubscriberManager(SubscriberManager subscriberManager) {
        this.subscriberManager = subscriberManager;
    }
}
