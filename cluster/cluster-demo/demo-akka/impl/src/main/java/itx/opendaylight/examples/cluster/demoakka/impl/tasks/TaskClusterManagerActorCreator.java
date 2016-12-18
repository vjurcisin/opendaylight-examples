/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.tasks;

import akka.japi.Creator;

/**
 * Created by gergej on 17.12.2016.
 */
public class TaskClusterManagerActorCreator implements Creator<TaskClusterManagerActor> {

    private TaskClusterManager taskClusterManager;

    public TaskClusterManagerActorCreator(TaskClusterManager taskClusterManager) {
        this.taskClusterManager = taskClusterManager;
    }

    @Override
    public TaskClusterManagerActor create() throws Exception {
        return new TaskClusterManagerActor(taskClusterManager);
    }
}
