/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.members;

/**
 * Created by gergej on 9.12.2016.
 */
public class MemberInfo {

    private String address;
    private MemberStatus status;

    public MemberInfo(String address, MemberStatus status) {
        this.address = address;
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public MemberStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "MEMBER: " + address + " status=" + status.name();
    }

}
