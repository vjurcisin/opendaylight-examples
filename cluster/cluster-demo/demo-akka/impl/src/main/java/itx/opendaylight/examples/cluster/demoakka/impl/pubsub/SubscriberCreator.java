/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.pubsub;

import akka.japi.Creator;

/**
 * Created by gergej on 8.12.2016.
 */
public class SubscriberCreator implements Creator<Subscriber> {

    private String topicName;

    public SubscriberCreator(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public Subscriber create() throws Exception {
        return new Subscriber(topicName);
    }

}
