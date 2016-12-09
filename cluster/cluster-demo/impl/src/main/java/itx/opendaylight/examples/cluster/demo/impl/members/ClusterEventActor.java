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
            clusterMemberManager.registerMember(((ClusterEvent.MemberJoined)o).member());
        } else if (o instanceof ClusterEvent.MemberRemoved) {
            clusterMemberManager.unregisterMember(((ClusterEvent.MemberJoined)o).member());
        } else if (o instanceof ClusterEvent.UnreachableMember) {
            clusterMemberManager.updateMember(((ClusterEvent.MemberJoined)o).member());
        } else if (o instanceof ClusterEvent.MemberExited) {
            clusterMemberManager.updateMember(((ClusterEvent.MemberJoined)o).member());
        } else if (o instanceof ClusterEvent.MemberLeft) {
            clusterMemberManager.updateMember(((ClusterEvent.MemberJoined)o).member());
        } else if (o instanceof ClusterEvent.MemberUp) {
            clusterMemberManager.updateMember(((ClusterEvent.MemberJoined)o).member());
        } else if (o instanceof ClusterEvent.MemberWeaklyUp) {
            clusterMemberManager.updateMember(((ClusterEvent.MemberJoined)o).member());
        } else {
            LOG.warn("unsupported event: " + o.getClass().getCanonicalName());
        }
    }

}
