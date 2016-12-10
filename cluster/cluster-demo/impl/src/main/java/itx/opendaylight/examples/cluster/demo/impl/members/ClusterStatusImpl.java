/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.members;

import java.util.List;

/**
 * Created by gergej on 10.12.2016.
 */
public class ClusterStatusImpl implements ClusterStatus {

    private String selfAddress;
    private List<MemberInfo> members;
    private Boolean leader;

    public ClusterStatusImpl(String selfAddress, List<MemberInfo> members, Boolean leader) {
        this.selfAddress = selfAddress;
        this.members = members;
        this.leader = leader;
    }

    @Override
    public String getSelfAddress() {
        return selfAddress;
    }

    @Override
    public List<MemberInfo> getMembers() {
        return members;
    }

    @Override
    public Boolean isLeader() {
        return leader;
    }

}
