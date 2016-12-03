/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.eventbus.impl;

import akka.actor.ActorRef;
import akka.event.japi.LookupEventBus;
import itx.opendaylight.examples.akka.demo.eventbus.api.MessageBase;

/**
 * @author gergej
 */
public class LookupBusImpl extends LookupEventBus<MessageBase, ActorRef, String> {

    // is used for extracting the classifier from the incoming events
    @Override public String classify(MessageBase event) {
        return event.getTopic();
    }

    // will be invoked for each event for all subscribers which registered themselves
    // for the event’s classifier
    @Override public void publish(MessageBase event, ActorRef subscriber) {
        subscriber.tell(event, ActorRef.noSender());
    }

    // must define a full order over the subscribers, expressed as expected from
    // `java.lang.Comparable.compare`
    @Override public int compareSubscribers(ActorRef a, ActorRef b) {
        return a.compareTo(b);
    }

    // determines the initial size of the index data structure
    // used internally (i.e. the expected number of different classifiers)
    @Override public int mapSize() {
        return 128;
    }

}