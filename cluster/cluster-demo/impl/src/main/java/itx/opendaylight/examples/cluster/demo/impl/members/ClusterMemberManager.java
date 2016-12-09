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

    private Map<String, Member> members;

    public ClusterMemberManager() {
        LOG.info("ClusterMemberManager");
        members = new ConcurrentHashMap<>();
    }

    public void registerMember(Member member) {
        String address = member.address().toString();
        LOG.info("registerMember: " + address);
        members.put(address, member);
    }

    public void unregisterMember(Member member) {
        String address = member.address().toString();
        LOG.info("unregisterMember: " + address);
        members.remove(address);
    }

    public void updateMember(Member member) {
        String address = member.address().toString();
        LOG.info("updateMember: " + address);
        members.put(address, member);
    }

    public List<Member> getMembers() {
        List<Member> result = new ArrayList<>();
        members.values().forEach( m -> {
            result.add(m);
        });
        return result;
    }

}
