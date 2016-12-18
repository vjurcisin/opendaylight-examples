/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.tasks;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.PoisonPill;
import itx.opendaylight.examples.cluster.demoakka.impl.genericpubsub.GenericPubSubService;
import itx.opendaylight.examples.cluster.demoakka.impl.members.ClusterEventListener;
import itx.opendaylight.examples.cluster.demoakka.impl.members.ClusterMemberManager;
import itx.opendaylight.examples.cluster.demoakka.impl.members.ClusterStatus;
import itx.opendaylight.examples.cluster.demoakka.impl.members.MemberStatus;
import itx.opendaylight.examples.cluster.demoakka.impl.tasks.dto.*;
import org.opendaylight.controller.cluster.ActorSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Created by gergej on 17.12.2016.
 */
public class TaskClusterManager implements ClusterEventListener, TaskEventListener, TaskEventPublister {

    private static final Logger LOG = LoggerFactory.getLogger(TaskClusterManager.class);
    public static final String TOPIC_NAME = "taskManagementTopic";

    private GenericPubSubService genericPubSubService;
    private ClusterMemberManager clusterMemberManager;
    private TaskManager taskManager;
    private ActorSystemProvider actorSystemProvider;
    private ActorRef actorRef;
    private ClusterStatus clusterStatus;
    private Map<String, TaskClusterManagerInfoImpl> taskDistribution;

    public TaskClusterManager(GenericPubSubService genericPubSubService, ClusterMemberManager clusterMemberManager,
                              TaskManager taskManager, ActorSystemProvider actorSystemProvider) {
        LOG.info("TaskClusterManager");
        this.genericPubSubService = genericPubSubService;
        this.clusterMemberManager = clusterMemberManager;
        this.taskManager = taskManager;
        this.actorSystemProvider = actorSystemProvider;
    }

    public void init() {
        LOG.info("init");
        clusterMemberManager.addListener(this);
        actorRef = genericPubSubService.subscribe(new TaskClusterManagerActorCreator(this), TaskClusterManagerActor.NAME, TOPIC_NAME);
    }

    public void destroy() {
        LOG.info("destroy");
        genericPubSubService.unSubscribe(actorRef, TOPIC_NAME);
        clusterMemberManager.removeListener(this);
        actorRef.tell(PoisonPill.getInstance(), null);
    }

    @Override
    public void onClusterDataInit(ClusterStatus clusterStatus) {
        LOG.info("onClusterDataInit");
        this.clusterStatus = clusterStatus;
        this.taskDistribution = new ConcurrentHashMap<>();
        clusterStatus.getMembers().forEach( m -> {
            if (MemberStatus.UP.equals(m.getStatus())) {
                taskDistribution.put(m.getAddress(), new TaskClusterManagerInfoImpl(m.getAddress(), 0));
            }
        });
        genericPubSubService.publish(TOPIC_NAME, new TaskManagementStatusRequest(clusterStatus.getSelfAddress()));
    }

    @Override
    public void onMemberAdd(ClusterStatus clusterStatus, String address, MemberStatus status) {
        LOG.info("onMemberAdd: " + address);
        this.clusterStatus = clusterStatus;
        if (MemberStatus.UP.equals(status)) {
            taskDistribution.put(address, new TaskClusterManagerInfoImpl(address, 0));
        }
    }

    @Override
    public void onMemberRemove(ClusterStatus clusterStatus, String address, MemberStatus status) {
        LOG.info("onMemberRemove: " + address);
        this.clusterStatus = clusterStatus;
        taskDistribution.remove(address);
    }

    @Override
    public void onMemberChanged(ClusterStatus clusterStatus, String address, MemberStatus status) {
        LOG.info("onMemberChanged: " + address);
        this.clusterStatus = clusterStatus;
        if (MemberStatus.UP.equals(status)) {
            taskDistribution.put(address, new TaskClusterManagerInfoImpl(address, 0));
        } else {
            taskDistribution.remove(address);
        }
    }

    @Override
    public void onStateChanged(ClusterStatus clusterStatus) {
        LOG.info("onStateChanged:");
        this.clusterStatus = clusterStatus;
        clusterStatus.getMembers().forEach( m -> {
            if (!MemberStatus.UP.equals(m.getStatus())) {
                taskDistribution.remove(m.getAddress());
            }
        });
    }

    @Override
    public void onLeaderChanged(ClusterStatus clusterStatus, String leaderAddress) {
        LOG.info("onLeaderChanged: " + leaderAddress);
        this.clusterStatus = clusterStatus;
    }

    @Override
    public void onTaskSubmittedEvent(TaskSubmittedEvent taskSubmittedEvent) {
        LOG.info("onTaskSubmittedEvent: " + taskSubmittedEvent.getTaskId());
        TaskClusterManagerInfoImpl taskClusterManagerInfo = taskDistribution.remove(taskSubmittedEvent.getManagerAddress());
        if (taskClusterManagerInfo != null) {
            taskDistribution.put(taskSubmittedEvent.getManagerAddress(), TaskClusterManagerInfoImpl.incrementTasksRunning(taskClusterManagerInfo));
        }
    }

    @Override
    public void onTaskDoneEvent(TaskDoneEvent taskDoneEvent) {
        LOG.info("onTaskDoneEvent: " + taskDoneEvent.getTaskId());
        TaskClusterManagerInfoImpl taskClusterManagerInfo = taskDistribution.remove(taskDoneEvent.getManagerAddress());
        if (taskClusterManagerInfo != null) {
            taskDistribution.put(taskDoneEvent.getManagerAddress(), TaskClusterManagerInfoImpl.decrementTasksRunning(taskClusterManagerInfo));
        }
    }

    @Override
    public void onTaskManagementStatusRequest(TaskManagementStatusRequest taskManagementStatusRequest) {
        if (clusterStatus.getSelfAddress().equals(taskManagementStatusRequest.getManagerAddress())) {
            return; //ignore request from myself
        }
        LOG.info("onTaskManagementStatusRequest");
        ActorSystem actorSystem = actorSystemProvider.getActorSystem();
        String taskManagerAddress = createTaskClusterManagerRemoteAddress(taskManagementStatusRequest.getManagerAddress());
        ActorSelection actorSelection = actorSystem.actorSelection(taskManagerAddress);
        TaskManagementStatusResponse taskManagementStatusResponse = new TaskManagementStatusResponse(new HashMap<>(taskDistribution));
        actorSelection.tell(taskManagementStatusResponse, null);
    }

    @Override
    public void onTaskManagementStatusResponse(TaskManagementStatusResponse taskManagementStatusResponse) {
        LOG.info("onTaskManagementStatusResponse");
        taskDistribution = taskManagementStatusResponse.getTaskDistribution();
    }

        @Override
    public void publishTaskSubmittedEvent(String taskId) {
        genericPubSubService.publish(TOPIC_NAME, new TaskSubmittedEvent(clusterStatus.getSelfAddress(), taskId));
    }

    @Override
    public void publishTaskDoneEvent(String taskId) {
        genericPubSubService.publish(TOPIC_NAME, new TaskDoneEvent(clusterStatus.getSelfAddress(), taskId));
    }

    public void onTaskCreateRequest(TaskCreateRequest taskCreateRequest) {
        if (isLeader()) {
            LOG.info("onTaskCreateRequest accepted: " + taskCreateRequest.toString());
            ActorSystem actorSystem = actorSystemProvider.getActorSystem();
            String targetNode = getTaskManagerAddressWithLowestLoad();
            String taskManagerAddress = createTaskClusterManagerRemoteAddress(targetNode);
            LOG.info("selected target actor: " + taskManagerAddress);
            ActorSelection actorSelection = actorSystem.actorSelection(taskManagerAddress);
            actorSelection.tell(TaskCreateData.from(taskCreateRequest), null);
        }
    }

    public void onTaskCreateData(TaskCreateData taskCreateData) {
        LOG.info("onTaskCreateData: " + taskCreateData);
        taskManager.submitTask(taskCreateData, this);
    }

    public TaskClusterManagerStatus getStatus() {
        Set<TaskClusterManagerInfo> members = taskDistribution.values().stream().collect(Collectors.toSet());
        return new TaskClusterManagerStatus(clusterStatus.isLeader(), clusterStatus.getSelfAddress(), members);
    }

    private boolean isLeader() {
        if (clusterStatus != null) {
            return clusterStatus.isLeader();
        }
        return false;
    }

    private boolean isInitialized() {
        return (clusterStatus != null);
    }

    private String getTaskManagerAddressWithLowestLoad() {
        String address = null;
        int lowest = Integer.MAX_VALUE;
        for (TaskClusterManagerInfoImpl taskClusterManagerInfo: taskDistribution.values()) {
            if (lowest > taskClusterManagerInfo.getTasksRunning()) {
                lowest = taskClusterManagerInfo.getTasksRunning();
                address = taskClusterManagerInfo.getAddress();
            }
        }
        return address;
    }

    private String createTaskClusterManagerRemoteAddress(String nodeAddress) {
        return nodeAddress + "/user/" + TaskClusterManagerActor.NAME;
    }

}
