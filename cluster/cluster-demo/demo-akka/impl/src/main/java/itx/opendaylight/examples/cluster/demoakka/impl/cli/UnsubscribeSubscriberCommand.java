/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.cli;

import itx.opendaylight.examples.cluster.demoakka.impl.pubsub.SubscriberManager;
import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 8.12.2016.
 */
@Command(scope = "pubsub", name = "unsubscribe", description = "unsubscribe new actor from topic")
public class UnsubscribeSubscriberCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(UnsubscribeSubscriberCommand.class);

    private SubscriberManager subscriberManager;

    @Argument(index = 0, name = "actorName",
            description = "name of actor to unsubscribe",
            required = true,
            multiValued = false)
    private String actorName;

    @Override
    protected Object doExecute() throws Exception {
        LOG.info("unsubscribe " + actorName);
        if (!subscriberManager.unsubscribe(actorName)) {
            return "ERROR";
        }
        return "OK";
    }

    public void setSubscriberManager(SubscriberManager subscriberManager) {
        this.subscriberManager = subscriberManager;
    }

}
