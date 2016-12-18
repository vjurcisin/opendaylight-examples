/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.tasks.dto;

import java.io.Serializable;

/**
 * Created by gergej on 17.12.2016.
 */
public class TaskInfo implements Serializable {

    private String taskId;
    private Long started;

    public TaskInfo(String taskId, Long started) {
        this.taskId = taskId;
        this.started = started;
    }

    public String getTaskId() {
        return taskId;
    }

    public Long getStarted() {
        return started;
    }

}
