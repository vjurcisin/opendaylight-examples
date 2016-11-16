/*
 * Copyright © 2016 Pantheon s.r.o and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package tech.pantheon.opendaylight.odlcamel.impl;

import javax.annotation.Nonnull;
import org.opendaylight.controller.md.sal.dom.api.DOMNotification;
import org.opendaylight.controller.md.sal.dom.api.DOMNotificationListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by juraj on 10.11.2016.
 */
public class NotificationListener implements DOMNotificationListener {

    private static final Logger LOG = LoggerFactory.getLogger(NotificationListener.class);

    @Override
    public void onNotification(@Nonnull DOMNotification notification) {
        LOG.info("onNotification: " + notification.getType().toString());
        LOG.info("onNotification: " + notification.getBody().toString());
    }

}
