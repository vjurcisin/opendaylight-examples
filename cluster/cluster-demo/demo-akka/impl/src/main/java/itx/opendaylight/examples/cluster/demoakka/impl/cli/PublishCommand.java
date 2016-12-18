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
import itx.opendaylight.examples.cluster.demoakka.impl.pubsub.PublisherCreator;
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
@Command(scope = "pubsub", name = "publish", description = "publish message to topic")
public class PublishCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(PublishCommand.class);

    private ActorSystemProvider actorSystemProvider;

    @Argument(index = 0, name = "topicName",
            description = "topicName to publish",
            required = true,
            multiValued = false)
    private String topicName;

    @Argument(index = 1, name = "message",
            description = "message to publish",
            required = true,
            multiValued = false)
    private String message;

    @Override
    protected Object doExecute() throws Exception {
        try {
            LOG.info("publish: " + topicName + "::" + message);
            String publisherName = "publisher-" + UUID.randomUUID().toString();
            ActorSystem actorSystem = actorSystemProvider.getActorSystem();
            //somewhere else
            ActorRef publisher = actorSystem.actorOf(Props.create(new PublisherCreator(topicName)), publisherName);
            // after a while the subscriptions are replicated
            publisher.tell(message, null);
            publisher.tell(akka.actor.PoisonPill.getInstance(), null);
            return "published: " + topicName + "::" + message;
        } catch (Exception e) {
            LOG.error("ERROR: ", e );
            return "ERROR";
        }
    }

    public void setActorSystemProvider(ActorSystemProvider actorSystemProvider) {
        this.actorSystemProvider = actorSystemProvider;
    }

}
