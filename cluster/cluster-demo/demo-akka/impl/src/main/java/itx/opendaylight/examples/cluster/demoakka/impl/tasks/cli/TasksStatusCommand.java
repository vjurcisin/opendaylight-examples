/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.tasks.cli;

import itx.opendaylight.examples.cluster.demoakka.impl.tasks.TaskClusterManager;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 17.12.2016.
 */
@Command(scope = "tasks", name = "list", description = "list task distribution and status")
public class TasksStatusCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(TasksStatusCommand.class);

    private TaskClusterManager taskClusterManager;

    @Override
    protected Object doExecute() throws Exception {
        String result = taskClusterManager.getStatus().toString();
        return result + "\n\nOK";
    }

    public void setTaskClusterManager(TaskClusterManager taskClusterManager) {
        this.taskClusterManager = taskClusterManager;
    }

}
