/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.tasks;

import akka.actor.UntypedActor;
import itx.opendaylight.examples.cluster.demo.impl.tasks.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 17.12.2016.
 */
public class TaskClusterManagerActor extends UntypedActor {

    private static final Logger LOG = LoggerFactory.getLogger(TaskClusterManagerActor.class);
    public static final String NAME = "TaskClusterManagerActor";

    private TaskClusterManager taskClusterManager;

    public TaskClusterManagerActor(TaskClusterManager taskClusterManager) {
        this.taskClusterManager = taskClusterManager;
        LOG.info("TaskClusterManagerActor: " + getSelf().path().address() + " " + getSelf().path().name());
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof TaskCreateRequest) {
            LOG.info("onReceive: TaskCreateRequest");
            taskClusterManager.onTaskCreateRequest((TaskCreateRequest) message);
        } else if (message instanceof TaskCreateData) {
            LOG.info("onReceive: TaskCreateData");
            taskClusterManager.onTaskCreateData((TaskCreateData)message);
        } else if (message instanceof TaskSubmittedEvent) {
            LOG.info("onReceive: TaskSubmittedEvent");
            taskClusterManager.onTaskSubmittedEvent((TaskSubmittedEvent)message);
        } else if (message instanceof TaskDoneEvent) {
            LOG.info("onReceive: TaskDoneEvent");
            taskClusterManager.onTaskDoneEvent((TaskDoneEvent) message);
        } else if (message instanceof TaskManagementStatusRequest) {
            LOG.info("onReceive: TaskManagementStatusRequest");
            taskClusterManager.onTaskManagementStatusRequest((TaskManagementStatusRequest)message);
        } else if (message instanceof TaskManagementStatusResponse) {
            taskClusterManager.onTaskManagementStatusResponse((TaskManagementStatusResponse)message);
        } else if (message instanceof String) {
            LOG.info("onReceive: not handled: " + message);
        } else {
            LOG.info("onReceive: not handled: " + message.getClass().getName());
        }
    }

}
