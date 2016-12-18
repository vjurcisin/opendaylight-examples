/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.tasks.dto;

import java.io.Serializable;

/**
 * Created by gergej on 17.12.2016.
 */
public class TaskDoneEvent implements Serializable {

    private String managerAddress;
    private String taskId;

    public TaskDoneEvent(String managerAddress, String taskId) {
        this.managerAddress = managerAddress;
        this.taskId = taskId;
    }

    public String getManagerAddress() {
        return managerAddress;
    }

    public String getTaskId() {
        return taskId;
    }

    @Override
    public String toString() {
        return "TaskDoneEvent: " + managerAddress + " " + taskId;
    }

}
