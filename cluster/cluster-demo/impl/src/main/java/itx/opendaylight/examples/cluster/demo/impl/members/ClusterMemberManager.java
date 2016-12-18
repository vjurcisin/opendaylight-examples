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
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by juraj on 9.12.2016.
 */
public class ClusterMemberManager {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterMemberManager.class);

    private String selfAddress;
    private boolean leader;
    private Map<String, MemberInfoImpl> members;
    private List<ClusterEventListener> listeners;
    private ClusterStatus clusterStatus;

    public ClusterMemberManager() {
        LOG.info("ClusterMemberManager");
        members = new ConcurrentHashMap<>();
        listeners = Collections.synchronizedList(new ArrayList<>());
        selfAddress = "";
        leader = false;
        clusterStatus = null;
    }

    public void registerMember(MemberStatus status, Member member) {
        String address = member.address().toString();
        LOG.info("registerMember: " + address + " memberStatus=" + member.status().toString() + " status=" + status.name());
        members.put(address, new MemberInfoImpl(address, status, false));
        createClusterStatus();
        listeners.forEach( l -> { l.onMemberAdd(clusterStatus, address, status); });
    }

    public void unregisterMember(MemberStatus status, Member member) {
        String address = member.address().toString();
        LOG.info("unregisterMember: " + address  + " memberStatus=" + member.status().toString() + " status=" + status.name());
        members.remove(address);
        createClusterStatus();
        listeners.forEach( l -> { l.onMemberRemove(clusterStatus, address, status); });
    }

    public void updateMember(MemberStatus status, Member member) {
        String address = member.address().toString();
        LOG.info("updateMember: " + address + " memberStatus=" + member.status().toString() + " status=" + status.name());
        members.put(address, new MemberInfoImpl(address, status, false));
        createClusterStatus();
        listeners.forEach( l -> { l.onMemberChanged(clusterStatus, address, status); });
    }

    public void initClusterState(ClusterEvent.CurrentClusterState clusterState, String selfAddress) {
        LOG.info("initClusterState");
        this.selfAddress = selfAddress;
        setClusterState(clusterState);
        createClusterStatus();
        listeners.forEach( l -> { l.onClusterDataInit(clusterStatus); });
    }

    public void leaderChanged(ClusterEvent.LeaderChanged leaderChanged) {
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
        if (selfAddress.equals(leaderAddress)) {
            leader = true;
        } else {
            leader = false;
        }
        createClusterStatus();
        listeners.forEach( l -> { l.onLeaderChanged(clusterStatus, leaderAddress); });
    }

    public void updateClusterState(ClusterEvent.CurrentClusterState clusterState) {
        LOG.info("updateClusterState");
        setClusterState(clusterState);
        createClusterStatus();
        listeners.forEach( l -> { l.onStateChanged(clusterStatus); });
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
        if (selfAddress.equals(leaderAddress)) {
            leader = true;
        } else {
            leader = false;
        }
    }

    public ClusterStatus getClusterStatus() {
        return clusterStatus;
    }

    public Boolean isLeader() {
        return leader;
    }

    public String getSelfAddress() {
        return selfAddress;
    }

    public void addListener(ClusterEventListener listener) {
        listeners.add(listener);
        listener.onClusterDataInit(clusterStatus);
    }

    public void removeListener(ClusterEventListener listener) {
        listeners.remove(listener);
    }

    private void createClusterStatus() {
        List<MemberInfo> memberList = new ArrayList<>();
        members.values().forEach( m -> {
            memberList.add(m);
        });
        clusterStatus = new ClusterStatusImpl(selfAddress, memberList, leader);
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
