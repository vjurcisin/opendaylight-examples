/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.members;

import akka.actor.UntypedActor;
import akka.cluster.ClusterEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by juraj on 9.12.2016.
 */
public class ClusterEventActor extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterEventActor.class);

    private ClusterMemberManager clusterMemberManager;

    public ClusterEventActor(ClusterMemberManager clusterMemberManager) {
        this.clusterMemberManager = clusterMemberManager;
    }

    @Override
    public void onReceive(Object o) throws Exception {
        if (o instanceof ClusterEvent.MemberJoined) {
            LOG.info("ClusterEvent.MemberJoined");
            clusterMemberManager.registerMember(MemberStatus.JOINED, ((ClusterEvent.MemberJoined)o).member());
        } else if (o instanceof ClusterEvent.MemberRemoved) {
            LOG.info("ClusterEvent.MemberRemoved");
            clusterMemberManager.unregisterMember(MemberStatus.REMOVED, ((ClusterEvent.MemberRemoved)o).member());
        } else if (o instanceof ClusterEvent.UnreachableMember) {
            LOG.info("ClusterEvent.UnreachableMember");
            clusterMemberManager.updateMember(MemberStatus.UNREACHABLE, ((ClusterEvent.UnreachableMember)o).member());
        } else if (o instanceof ClusterEvent.MemberExited) {
            LOG.info("ClusterEvent.MemberExited");
            clusterMemberManager.updateMember(MemberStatus.EXITED, ((ClusterEvent.MemberExited)o).member());
        } else if (o instanceof ClusterEvent.MemberLeft) {
            LOG.info("ClusterEvent.MemberLeft");
            clusterMemberManager.updateMember(MemberStatus.LEFT, ((ClusterEvent.MemberLeft)o).member());
        } else if (o instanceof ClusterEvent.MemberUp) {
            LOG.info("ClusterEvent.MemberUp");
            clusterMemberManager.updateMember(MemberStatus.UP, ((ClusterEvent.MemberUp)o).member());
        } else if (o instanceof ClusterEvent.MemberWeaklyUp) {
            LOG.info("ClusterEvent.MemberWeaklyUp");
            clusterMemberManager.updateMember(MemberStatus.WEAKLYUP, ((ClusterEvent.MemberWeaklyUp) o).member());
        } else if (o instanceof ClusterEvent.CurrentClusterState) {
            ClusterEvent.CurrentClusterState clusterState = (ClusterEvent.CurrentClusterState)o;
            LOG.info("ClusterEvent.CurrentClusterState: leader=" + clusterState.getLeader().toString());
        } else {
            LOG.warn("ClusterEvent: unsupported event: " + o.getClass().getCanonicalName());
        }
    }

}
