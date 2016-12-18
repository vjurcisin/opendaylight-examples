/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.tasks;

import itx.opendaylight.examples.cluster.demo.impl.tasks.dto.TaskCreateData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by gergej on 17.12.2016.
 */
public class TaskManager {

    private static final Logger LOG = LoggerFactory.getLogger(TaskManager.class);

    private ExecutorService executor;

    public void init() {
        LOG.info("init");
        executor = Executors.newFixedThreadPool(100);
    }

    public void destroy() {
        LOG.info("destroy");
        executor.shutdown();
    }

    public void submitTask(TaskCreateData taskCreateData, TaskEventPublister taskEventPublister) {
        Task task = new Task(taskCreateData.getName(), taskCreateData.getCount(), taskCreateData.getDelay(), taskEventPublister);
        executor.submit(task);
    }

}
