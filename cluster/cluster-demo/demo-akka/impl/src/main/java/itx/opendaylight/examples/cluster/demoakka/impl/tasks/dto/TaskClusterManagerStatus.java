/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.tasks.dto;

import java.io.Serializable;
import java.util.Set;

/**
 * Created by gergej on 17.12.2016.
 */
public class TaskClusterManagerStatus implements Serializable {

    private Boolean isLeader;
    private String address;
    private Set<TaskClusterManagerInfo> members;

    public TaskClusterManagerStatus(Boolean isLeader, String address, Set<TaskClusterManagerInfo> members) {
        this.isLeader = isLeader;
        this.address = address;
        this.members = members;
    }

    public Boolean getLeader() {
        return isLeader;
    }

    public String getAddress() {
        return address;
    }

    public Set<TaskClusterManagerInfo> getMembers() {
        return members;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("TCM: ");
        sb.append(address);
        sb.append(" leader=");
        sb.append(isLeader);
        sb.append("\n");
        members.forEach(m -> {
            sb.append("\tmember:");
            sb.append(m.getAddress());
            sb.append(" tasks=");
            sb.append(m.getTasksRunning());
            sb.append("\n");
        });
        return sb.toString();
    }

}
