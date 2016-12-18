/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.pubsub;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 8.12.2016.
 */
public class Subscriber extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(Subscriber.class);

    private String topicName;

    public Subscriber(String topicName) {
        LOG.info("Subscriber: " + topicName);
        this.topicName = topicName;
        ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();
        // subscribe to the topic named "content"
        mediator.tell(new DistributedPubSubMediator.Subscribe(topicName, getSelf()), getSelf());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof String) {
            LOG.info("Received: " + topicName + "::" + message);
        } else if (message instanceof DistributedPubSubMediator.SubscribeAck) {
            LOG.info("subscribing");
        } else {
            unhandled(message);
        }
    }
}
