/*
 * Copyright © 2016 Pantheon s.r.o and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package tech.pantheon.opendaylight.odlcamel.impl.component;

import org.apache.camel.Exchange;
import org.apache.camel.impl.DefaultProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The odlcamel producer.
 */
public class OdlCamelProducer extends DefaultProducer {

    private static final transient Logger LOG = LoggerFactory.getLogger(OdlCamelProducer.class);
    private OdlCamelEndpoint endpoint;

    public OdlCamelProducer(OdlCamelEndpoint endpoint) {
        super(endpoint);
        LOG.info("OdlCamelProducer");
        this.endpoint = endpoint;
    }

    public void process(Exchange exchange) throws Exception {
        LOG.info("process: " + exchange.getIn().getBody().toString());
    }

}
