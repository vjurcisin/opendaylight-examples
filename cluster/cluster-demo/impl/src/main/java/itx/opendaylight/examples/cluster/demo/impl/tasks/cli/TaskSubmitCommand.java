/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.tasks.cli;

import itx.opendaylight.examples.cluster.demo.impl.genericpubsub.GenericPubSubService;
import itx.opendaylight.examples.cluster.demo.impl.tasks.TaskClusterManager;
import itx.opendaylight.examples.cluster.demo.impl.tasks.dto.TaskCreateRequest;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 17.12.2016.
 */
@Command(scope = "tasks", name = "submit", description = "submit new task")
public class TaskSubmitCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(TasksStatusCommand.class);

    private GenericPubSubService genericPubSubService;

    @Argument(index = 0, name = "name",
            description = "task name",
            required = true,
            multiValued = false)
    private String name;

    @Argument(index = 1, name = "count",
            description = "task count (integer)",
            required = true,
            multiValued = false)
    private Integer count;

    @Argument(index = 2, name = "delay",
            description = "task delay (long in milliseconds)",
            required = true,
            multiValued = false)
    private Long delay;

    @Override
    protected Object doExecute() throws Exception {
        TaskCreateRequest taskCreateRequest = new TaskCreateRequest(name, count, delay);
        genericPubSubService.publish(TaskClusterManager.TOPIC_NAME, taskCreateRequest);
        return "OK";
    }

    public void setGenericPubSubService(GenericPubSubService genericPubSubService) {
        this.genericPubSubService = genericPubSubService;
    }

}
