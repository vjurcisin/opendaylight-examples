/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl.cli;

import akka.actor.ActorSystem;
import itx.opendaylight.examples.cluster.demo.impl.singleton.AkkaClusterSingletonService;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.opendaylight.controller.cluster.ActorSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * Created by gergej on 10.12.2016.
 */
@Command(scope = "cluster", name = "tellSingleton", description = "send message to singleton")
public class TellSingletonCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(TellSingletonCommand.class);

    private AkkaClusterSingletonService akkaClusterSingletonService;

    @Argument(index = 0, name = "message",
            description = "message for cluster singleton",
            required = true,
            multiValued = false)
    private String message;

    @Override
    protected Object doExecute() throws Exception {
        LOG.info("tell singleton command: " + message);
        akkaClusterSingletonService.tell(message, null);
        return "OK";
    }

    public void setAkkaClusterSingletonService(AkkaClusterSingletonService akkaClusterSingletonService) {
        this.akkaClusterSingletonService = akkaClusterSingletonService;
    }

}
