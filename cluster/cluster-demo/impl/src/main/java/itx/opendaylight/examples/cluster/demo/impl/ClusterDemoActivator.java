/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.cluster.Cluster;
import akka.cluster.ClusterEvent;
import akka.cluster.Member;
import itx.opendaylight.examples.cluster.demo.impl.members.ClusterEventActorCreator;
import itx.opendaylight.examples.cluster.demo.impl.members.ClusterMemberManager;
import itx.opendaylight.examples.cluster.demo.impl.members.MemberInfo;
import itx.opendaylight.examples.cluster.demo.impl.members.MemberStatus;
import itx.opendaylight.examples.cluster.demo.impl.pubsub.SubscriberCreator;
import itx.opendaylight.examples.cluster.demo.impl.pubsub.SubscriberManager;
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
        LOG.info("init");

        //create default topic subscriber
        actorName = "subscriber-" + UUID.randomUUID().toString();
        ActorSystem actorSystem = this.actorSystemProvider.getActorSystem();
        ActorRef actorRef = actorSystem.actorOf(Props.create(new SubscriberCreator(DEFAULT_TOPIC)), actorName);
        subscriberManager.subscribe(actorRef, actorName, DEFAULT_TOPIC);
        LOG.info("Actor created: " + actorName);

        //initialize member manager
        final Cluster cluster = Cluster.get(actorSystem);
        clusterActorRef =
                actorSystem.actorOf(Props.create(new ClusterEventActorCreator(clusterMemberManager)), "cluster-event-actor");
        cluster.subscribe(clusterActorRef, ClusterEvent.MemberEvent.class, ClusterEvent.UnreachableMember.class);
        cluster.state().getMembers().forEach(
                m -> {
                    MemberStatus status = resolveMemberStatus(m);
                    clusterMemberManager.registerMember(status, m);
                }
        );
    }

    public void destroy() {
        LOG.info("destroy");
        subscriberManager.unsubscribe(actorName);

        ActorSystem actorSystem = this.actorSystemProvider.getActorSystem();
        final Cluster cluster = Cluster.get(actorSystem);
        cluster.unsubscribe(clusterActorRef);
    }

    private MemberStatus resolveMemberStatus(Member member) {
        MemberStatus status = MemberStatus.NA;
        if ("UP".equals(member.status().toString().toUpperCase())) {
            status = MemberStatus.UP;
        } else if ("LEFT".equals(member.status().toString().toUpperCase())) {
            status = MemberStatus.LEFT;
        } else if ("UNREACHABLE".equals(member.status().toString().toUpperCase())) {
            status = MemberStatus.UNREACHABLE;
        } else if ("EXITED".equals(member.status().toString().toUpperCase())) {
            status = MemberStatus.EXITED;
        } else if ("REMOVED".equals(member.status().toString().toUpperCase())) {
            status = MemberStatus.REMOVED;
        } else if ("JOINED".equals(member.status().toString().toUpperCase())) {
            status = MemberStatus.JOINED;
        } else if ("WEAKLYUP".equals(member.status().toString().toUpperCase())) {
            status = MemberStatus.WEAKLYUP;
        }
        return status;
    }

}
