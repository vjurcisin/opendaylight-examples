/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.cli;

import itx.opendaylight.examples.cluster.demoakka.impl.ODLClusterSingleton;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 8.12.2016.
 */
@Command(scope = "cluster", name = "showOdlLeader", description = "show if we are cluster leader")
public class ShowODLLeaderCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(ShowODLLeaderCommand.class);

    private ODLClusterSingleton odlClusterSingleton;

    @Override
    protected Object doExecute() throws Exception {
        try {
            boolean isLeader = odlClusterSingleton.isLeader();
            LOG.info("isLeader: " + isLeader);
            return "isLeader: " + isLeader;
        } catch (Exception e) {
            LOG.error("ERROR: ", e );
            return "ERROR";
        }
    }

    public void setOdlClusterSingleton(ODLClusterSingleton odlClusterSingleton) {
        this.odlClusterSingleton = odlClusterSingleton;
    }

}
