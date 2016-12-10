/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.singleton;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.cluster.singleton.ClusterSingletonProxy;
import akka.cluster.singleton.ClusterSingletonProxySettings;
import org.opendaylight.controller.cluster.ActorSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 10.12.2016.
 */
public class AkkaClusterSingletonService {

    private static final Logger LOG = LoggerFactory.getLogger(AkkaClusterSingletonService.class);

    private ActorRef singletonactorreference;
    private ActorSystemProvider actorSystemProvider;

    public AkkaClusterSingletonService(ActorSystemProvider actorSystemProvider) {
        this.actorSystemProvider = actorSystemProvider;
    }

    public void init() {
        LOG.info("init ...");
        ActorSystem actorSystem = this.actorSystemProvider.getActorSystem();
        ClusterSingletonProxySettings proxySettings = ClusterSingletonProxySettings.create(actorSystem);
        singletonactorreference = actorSystem.actorOf(
                ClusterSingletonProxy.props("/user/the-singleton", proxySettings),
                "the-singleton-proxy");
        LOG.info("singletonactorreference: " + (singletonactorreference != null));
    }

    public void destroy() {
        LOG.info("destroy ...");
    }

    public void tell(Object msg, ActorRef sender) {
        LOG.info("tell singleton: " + msg.toString());
        singletonactorreference.tell(msg, sender);
    }

}
