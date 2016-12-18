/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.tasks;

import itx.opendaylight.examples.cluster.demoakka.impl.tasks.dto.TaskDoneEvent;
import itx.opendaylight.examples.cluster.demoakka.impl.tasks.dto.TaskManagementStatusRequest;
import itx.opendaylight.examples.cluster.demoakka.impl.tasks.dto.TaskManagementStatusResponse;
import itx.opendaylight.examples.cluster.demoakka.impl.tasks.dto.TaskSubmittedEvent;

/**
 * Created by gergej on 17.12.2016.
 */
public interface TaskEventListener {

    public void onTaskSubmittedEvent(TaskSubmittedEvent taskSubmittedEvent);

    public void onTaskDoneEvent(TaskDoneEvent taskDoneEvent);

    public void onTaskManagementStatusRequest(TaskManagementStatusRequest taskManagementStatusRequest);

    public void onTaskManagementStatusResponse(TaskManagementStatusResponse taskManagementStatusResponse);

}
