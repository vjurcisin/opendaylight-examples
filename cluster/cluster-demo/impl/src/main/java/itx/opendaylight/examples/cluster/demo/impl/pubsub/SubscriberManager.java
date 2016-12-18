/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.pubsub;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.japi.Creator;
import org.opendaylight.controller.cluster.ActorSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by gergej on 8.12.2016.
 */
public class SubscriberManager {

    private static final Logger LOG = LoggerFactory.getLogger(SubscriberManager.class);

    private Map<String, ActorRef> actorsByName;
    private Map<String, List<String>> actorsByTopic;

    public SubscriberManager() {
        actorsByName = new ConcurrentHashMap<>();
        actorsByTopic = new ConcurrentHashMap<>();
    }

    public void subscribe(ActorRef actorRef, String actorName, String topicName) {
        LOG.info("subscribe: " + actorName + "::" + topicName);
        actorsByName.put(actorName, actorRef);
        List<String> actorNamesForTopic = actorsByTopic.get(topicName);
        if (actorNamesForTopic == null) {
            actorNamesForTopic = Collections.synchronizedList(new ArrayList<String>());
            actorsByTopic.put(topicName, actorNamesForTopic);
        }
        actorNamesForTopic.add(actorName);
    }

    public List<ActorInfo> getSubscribers() {
        LOG.info("getSubscribers:");
        List<ActorInfo> result = new ArrayList<>();
        actorsByTopic.forEach((t,ns) -> {
            ns.forEach( n -> {
                result.add(new ActorInfo(n,t));
            });
        });
        return result;
    }

    public boolean unsubscribe(String actorName) {
        boolean result = false;
        LOG.info("unsubscribe: " + actorName);
        ActorRef actorRef = actorsByName.remove(actorName);
        if (actorRef != null) {
            result = true;
            actorRef.tell(akka.actor.PoisonPill.getInstance(), null);
        }
        actorsByTopic.forEach((t,n) -> {
            n.remove(actorName);
        });
        return result;
    }

}
