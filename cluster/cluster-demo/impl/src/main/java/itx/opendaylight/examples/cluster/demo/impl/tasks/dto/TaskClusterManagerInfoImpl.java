/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.tasks.dto;

/**
 * Created by gergej on 17.12.2016.
 */
public class TaskClusterManagerInfoImpl implements TaskClusterManagerInfo {

    private String address;
    private int tasksRunning;

    public TaskClusterManagerInfoImpl(String address, int tasksRunning) {
        this.address = address;
        this.tasksRunning = tasksRunning;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public int getTasksRunning() {
        return tasksRunning;
    }

    public static TaskClusterManagerInfoImpl incrementTasksRunning(TaskClusterManagerInfoImpl instance) {
        return new TaskClusterManagerInfoImpl(instance.getAddress(), (instance.getTasksRunning() + 1));
    }

    public static TaskClusterManagerInfoImpl decrementTasksRunning(TaskClusterManagerInfoImpl instance) {
        return new TaskClusterManagerInfoImpl(instance.getAddress(), (instance.getTasksRunning() - 1));
    }

    public static TaskClusterManagerInfoImpl from(TaskClusterManagerInfoImpl instance) {
        return new TaskClusterManagerInfoImpl(instance.getAddress(), instance.getTasksRunning());
    }

}
