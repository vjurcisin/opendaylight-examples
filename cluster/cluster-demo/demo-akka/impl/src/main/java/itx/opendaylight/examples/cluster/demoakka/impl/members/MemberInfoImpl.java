/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.members;

/**
 * Created by gergej on 10.12.2016.
 */
public class MemberInfoImpl implements MemberInfo {

    private String address;
    private MemberStatus status;
    private Boolean leader;

    public MemberInfoImpl(String address, MemberStatus status, Boolean leader) {
        this.address = address;
        this.status = status;
        this.leader = leader;
    }

    public String getAddress() {
        return address;
    }

    public MemberStatus getStatus() {
        return status;
    }

    public Boolean isLeader() {
        return leader;
    }

    public void setLeader(Boolean leader) {
        this.leader = leader;
    }

    @Override
    public String toString() {
        return "MEMBER: " + address + " status=" + status.name() + " leader=" + leader;
    }

}
