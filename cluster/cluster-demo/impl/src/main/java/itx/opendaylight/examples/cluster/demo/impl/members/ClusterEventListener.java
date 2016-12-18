/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.members;

/**
 * Created by gergej on 17.12.2016.
 */
public interface ClusterEventListener {

    public void onClusterDataInit(ClusterStatus clusterStatus);

    public void onMemberAdd(ClusterStatus clusterStatus, String address, MemberStatus status);

    public void onMemberRemove(ClusterStatus clusterStatus, String address, MemberStatus status);

    public void onMemberChanged(ClusterStatus clusterStatus, String address, MemberStatus status);

    public void onStateChanged(ClusterStatus clusterStatus);

    public void onLeaderChanged(ClusterStatus clusterStatus, String leaderAddress);

}
