/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.members;

import akka.cluster.Member;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by juraj on 9.12.2016.
 */
public class ClusterMemberManager {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterMemberManager.class);

    private Map<String, MemberInfo> members;

    public ClusterMemberManager() {
        LOG.info("ClusterMemberManager");
        members = new ConcurrentHashMap<>();
    }

    public void registerMember(MemberStatus status, Member member) {
        String address = member.address().toString();
        LOG.info("registerMember: " + address + " memberStatus=" + member.status().toString() + "status=" + status.name());
        members.put(address, new MemberInfo(address, status));
    }

    public void unregisterMember(MemberStatus status, Member member) {
        String address = member.address().toString();
        LOG.info("unregisterMember: " + address  + " memberStatus=" + member.status().toString() + "status=" + status.name());
        members.remove(address);
    }

    public void updateMember(MemberStatus status, Member member) {
        String address = member.address().toString();
        LOG.info("updateMember: " + address + " memberStatus=" + member.status().toString() + "status=" + status.name());
        members.put(address, new MemberInfo(address, status));
    }

    public List<MemberInfo> getMembers() {
        List<MemberInfo> result = new ArrayList<>();
        members.values().forEach( m -> {
            result.add(m);
        });
        return result;
    }

}
