/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.cli;

import itx.opendaylight.examples.cluster.demoakka.impl.pubsub.ActorInfo;
import itx.opendaylight.examples.cluster.demoakka.impl.pubsub.SubscriberManager;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by gergej on 8.12.2016.
 */
@Command(scope = "pubsub", name = "list", description = "subscribe new actor to topic")
public class ListSubscribersCommand extends OsgiCommandSupport {

    private static final Logger LOG = LoggerFactory.getLogger(PublishCommand.class);

    private SubscriberManager subscriberManager;

    @Override
    protected Object doExecute() throws Exception {
        LOG.info("list subscribers");
        StringBuffer sb = new StringBuffer();
        List<ActorInfo> ais =  subscriberManager.getSubscribers();
        sb.append("subscribers: [");
        sb.append(ais.size());
        sb.append("]\n");
        ais.forEach( ai -> {
            sb.append(ai.toString());
            sb.append("\n");
        });
        sb.append("\nOK\n");
        return sb.toString();
    }

    public void setSubscriberManager(SubscriberManager subscriberManager) {
        this.subscriberManager = subscriberManager;
    }

}
