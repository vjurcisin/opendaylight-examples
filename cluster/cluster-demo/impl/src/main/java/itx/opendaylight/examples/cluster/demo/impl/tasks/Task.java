/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.tasks;

import itx.opendaylight.examples.cluster.demo.impl.tasks.dto.TaskResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Created by gergej on 17.12.2016.
 */
public class Task implements Callable<TaskResult> {

    private static final Logger LOG = LoggerFactory.getLogger(Task.class);

    private String id;
    private Integer count;
    private long started;
    private Long delay;
    private TaskEventPublister taskEventPublister;

    public Task(String id, Integer count, Long delay, TaskEventPublister taskEventPublister) {
        this.id = id;
        this.count = count;
        this.delay = delay;
        this.taskEventPublister = taskEventPublister;
    }

    @Override
    public TaskResult call() throws Exception {
        taskEventPublister.publishTaskSubmittedEvent(id);
        started = System.currentTimeMillis();
        LOG.info(String.format("TASK STARTED: %s:%d:%dms", id, count, delay));
        for (int i=0; i<count; i++) {
            Thread.sleep(delay);
            LOG.info(String.format("TASK RUNNING: %s:[%d]/%d", id, i, count));
        }
        LOG.info(String.format("TASK FINISHED: %s:%d:%dms", id, count, delay));
        long duration = System.currentTimeMillis() - started;
        taskEventPublister.publishTaskDoneEvent(id);
        return new TaskResult(id, started, "FINISHED_OK", duration, count);
    }

}
