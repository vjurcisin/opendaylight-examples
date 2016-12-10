/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.members;

import akka.cluster.ClusterEvent;
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

    private Map<String, MemberInfoImpl> members;

    public ClusterMemberManager() {
        LOG.info("ClusterMemberManager");
        members = new ConcurrentHashMap<>();
    }

    public void registerMember(MemberStatus status, Member member) {
        String address = member.address().toString();
        LOG.info("registerMember: " + address + " memberStatus=" + member.status().toString() + " status=" + status.name());
        members.put(address, new MemberInfoImpl(address, status, false));
    }

    public void unregisterMember(MemberStatus status, Member member) {
        String address = member.address().toString();
        LOG.info("unregisterMember: " + address  + " memberStatus=" + member.status().toString() + " status=" + status.name());
        members.remove(address);
    }

    public void updateMember(MemberStatus status, Member member) {
        String address = member.address().toString();
        LOG.info("updateMember: " + address + " memberStatus=" + member.status().toString() + " status=" + status.name());
        members.put(address, new MemberInfoImpl(address, status, false));
    }

    public void initClusterState(ClusterEvent.CurrentClusterState clusterState) {
        LOG.info("initClusterState");
        setClusterState(clusterState);
    }

    public void leaderLeaderChanged(ClusterEvent.LeaderChanged leaderChanged) {
        members.values().forEach( member -> {
            member.setLeader(false);
        });
        String leaderAddress = leaderChanged.getLeader().toString();
        LOG.info("onRoleLeaderChanged: new leader: " + leaderAddress);
        MemberInfo memberinfo = members.remove(leaderAddress);
        if (memberinfo != null) {
            members.put(leaderAddress, new MemberInfoImpl(leaderAddress, memberinfo.getStatus(), true));
        } else {
            LOG.error("unknown member: " + leaderAddress);
        }
    }

    public void updateClusterState(ClusterEvent.CurrentClusterState clusterState) {
        LOG.info("updateClusterState");
        setClusterState(clusterState);
    }

    private void setClusterState(ClusterEvent.CurrentClusterState clusterState) {
        members = new ConcurrentHashMap<>();
        String leaderAddress = clusterState.getLeader().toString();
        clusterState.getMembers().forEach( member -> {
            String memberAddress = member.address().toString();
            MemberStatus status = resolveMemberStatus(member);
            if (leaderAddress.equals(memberAddress)) {
                members.put(memberAddress, new MemberInfoImpl(memberAddress, status, true));
            } else {
                members.put(memberAddress, new MemberInfoImpl(memberAddress, status, false));
            }
        } );

    }

    public List<MemberInfo> getMembers() {
        List<MemberInfo> result = new ArrayList<>();
        members.values().forEach( m -> {
            result.add(m);
        });
        return result;
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
