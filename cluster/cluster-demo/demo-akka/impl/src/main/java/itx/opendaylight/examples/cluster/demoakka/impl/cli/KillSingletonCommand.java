/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.cli;

import akka.actor.PoisonPill;
import itx.opendaylight.examples.cluster.demoakka.impl.singleton.AkkaClusterSingletonService;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 10.12.2016.
 */
@Command(scope = "cluster", name = "killSingleton", description = "send stop message to singleton actor")
public class KillSingletonCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(KillSingletonCommand.class);

    private AkkaClusterSingletonService akkaClusterSingletonService;

    @Override
    protected Object doExecute() throws Exception {
        LOG.info("kill singleton");
        akkaClusterSingletonService.tell(PoisonPill.getInstance(), null);
        return "OK";
    }

    public void setAkkaClusterSingletonService(AkkaClusterSingletonService akkaClusterSingletonService) {
        this.akkaClusterSingletonService = akkaClusterSingletonService;
    }

}
