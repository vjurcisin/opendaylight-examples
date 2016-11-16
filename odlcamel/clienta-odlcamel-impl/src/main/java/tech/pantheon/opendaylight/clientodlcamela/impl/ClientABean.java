/*
 * Copyright © 2016 Pantheon s.r.o and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package tech.pantheon.opendaylight.clientodlcamela.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by juraj on 16.11.2016.
 */
public class ClientABean {

    private static final Logger LOG = LoggerFactory.getLogger(ClientABean.class);

    public void init() {
        LOG.info("init");
    }

    public void destroy() {
        LOG.info("destroy");
    }

}
