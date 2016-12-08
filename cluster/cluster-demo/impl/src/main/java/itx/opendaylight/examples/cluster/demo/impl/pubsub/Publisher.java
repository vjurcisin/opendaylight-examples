/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.pubsub;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 8.12.2016.
 */
public class Publisher extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(Publisher.class);

    private ActorRef mediator;
    private String topicName;

    public Publisher(String topicName) {
        LOG.info("Publisher: " + topicName);
        this.mediator = DistributedPubSub.get(getContext().system()).mediator();
        this.topicName = topicName;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        LOG.info("onReceive");
        if (message instanceof String) {
            String in = (String) message;
            String out = in.toUpperCase();
            mediator.tell(new DistributedPubSubMediator.Publish(topicName, out),
                    getSelf());
        } else {
            unhandled(message);
        }
    }
}
