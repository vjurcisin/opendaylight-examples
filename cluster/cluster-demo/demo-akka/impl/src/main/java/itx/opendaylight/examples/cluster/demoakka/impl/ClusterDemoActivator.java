/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.singleton.ClusterSingletonManager;
import akka.cluster.singleton.ClusterSingletonManagerSettings;
import itx.opendaylight.examples.cluster.demoakka.impl.members.ClusterEventActorCreator;
import itx.opendaylight.examples.cluster.demoakka.impl.members.ClusterMemberManager;
import itx.opendaylight.examples.cluster.demoakka.impl.pubsub.SubscriberCreator;
import itx.opendaylight.examples.cluster.demoakka.impl.pubsub.SubscriberManager;
import itx.opendaylight.examples.cluster.demoakka.impl.singleton.SingletonConsumer;
import org.opendaylight.controller.cluster.ActorSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by gergej on 8.12.2016.
 */
public class ClusterDemoActivator {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterDemoActivator.class);
    public static final String DEFAULT_TOPIC = "defaultTopic";

    private ActorSystemProvider actorSystemProvider;
    private SubscriberManager subscriberManager;
    private ClusterMemberManager clusterMemberManager;
    private String actorName;
    private ActorRef clusterActorRef;

    public ClusterDemoActivator(ActorSystemProvider actorSystemProvider,
                                SubscriberManager subscriberManager, ClusterMemberManager clusterMemberManager) {
        LOG.info("ClusterDemoActivator");
        this.actorSystemProvider = actorSystemProvider;
        this.subscriberManager = subscriberManager;
        this.clusterMemberManager = clusterMemberManager;
    }

    public void init() {
        LOG.info("init ...");

        //create default topic subscriber
        LOG.info("creating default topic subscriber");
        actorName = "subscriber-" + UUID.randomUUID().toString();
        ActorSystem actorSystem = this.actorSystemProvider.getActorSystem();
        ActorRef actorRef = actorSystem.actorOf(Props.create(new SubscriberCreator(DEFAULT_TOPIC)), actorName);
        subscriberManager.subscribe(actorRef, actorName, DEFAULT_TOPIC);
        LOG.info("Actor created: " + actorName);

        //initialize member manager
        LOG.info("initializing cluster member manager");
        final Cluster cluster = Cluster.get(actorSystem);
        clusterActorRef =
                actorSystem.actorOf(Props.create(new ClusterEventActorCreator(clusterMemberManager)), "cluster-event-actor");
        cluster.subscribe(clusterActorRef,
                ClusterEvent.MemberEvent.class,
                ClusterEvent.UnreachableMember.class);
        clusterMemberManager.initClusterState(cluster.state(), cluster.selfAddress().toString());

        //akka singleton initialization
        LOG.info("initializing akka singleton");
        final ClusterSingletonManagerSettings settings = ClusterSingletonManagerSettings.create(actorSystem);
        actorSystem.actorOf(ClusterSingletonManager.props(
                Props.create(SingletonConsumer.class),
                PoisonPill.getInstance(), settings), "the-singleton");
    }

    public void destroy() {
        LOG.info("destroy");
        subscriberManager.unsubscribe(actorName);

        ActorSystem actorSystem = this.actorSystemProvider.getActorSystem();
        final Cluster cluster = Cluster.get(actorSystem);
        cluster.unsubscribe(clusterActorRef);
    }

}
