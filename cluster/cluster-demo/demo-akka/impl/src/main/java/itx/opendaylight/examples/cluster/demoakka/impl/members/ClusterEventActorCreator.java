/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.members;

import akka.japi.Creator;

/**
 * Created by juraj on 9.12.2016.
 */
public class ClusterEventActorCreator implements Creator<ClusterEventActor> {

    public ClusterMemberManager clusterMemberManager;

    public ClusterEventActorCreator(ClusterMemberManager clusterMemberManager) {
        this.clusterMemberManager = clusterMemberManager;
    }

    @Override
    public ClusterEventActor create() throws Exception {
        return new ClusterEventActor(clusterMemberManager);
    }

}
