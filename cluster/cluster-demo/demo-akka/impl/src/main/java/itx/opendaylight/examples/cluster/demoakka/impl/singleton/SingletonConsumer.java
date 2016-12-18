/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.singleton;

import akka.actor.PoisonPill;
import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 10.12.2016.
 */
public class SingletonConsumer extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(SingletonConsumer.class);

    public SingletonConsumer() {
        LOG.info("SINGLETON initializing");
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof PoisonPill) {
            LOG.info("SINGLETON: terminating now !");
        } else {
            LOG.info("SINGLETON: " + message.toString());
        }
    }

}
