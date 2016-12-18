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
 * Created by gergej on 18.12.2016.
 */
public class TaskCreateData implements Serializable {

    private String name;
    private Integer count;
    private Long delay;

    public TaskCreateData(String name, Integer count, Long delay) {
        this.name = name;
        this.count = count;
        this.delay = delay;
    }

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }

    public Long getDelay() {
        return delay;
    }

    public static TaskCreateData from(TaskCreateRequest taskCreateRequest) {
        return new TaskCreateData(taskCreateRequest.getName(), taskCreateRequest.getCount(), taskCreateRequest.getDelay());
    }

}
