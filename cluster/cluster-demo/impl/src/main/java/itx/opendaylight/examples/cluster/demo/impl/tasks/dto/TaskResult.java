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
public class TaskResult extends TaskInfo implements Serializable {

    private String status;
    private Long duration;
    private Integer counter;

    public TaskResult(String taskId, Long started, String status, Long duration, Integer counter) {
        super(taskId, started);
        this.status = status;
        this.duration = duration;
        this.counter = counter;
    }

    public String getStatus() {
        return status;
    }

    public Long getDuration() {
        return duration;
    }

    public Integer getCounter() {
        return counter;
    }

}
