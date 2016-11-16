/*
 * Copyright © 2016 Pantheon s.r.o and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package tech.pantheon.opendaylight.odlcamel.impl.component;

import org.apache.camel.Consumer;
import org.apache.camel.Processor;
import org.apache.camel.Producer;
import org.apache.camel.impl.DefaultEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a odlcamel endpoint.
 */
public class OdlCamelEndpoint extends DefaultEndpoint {

    private static final Logger LOG = LoggerFactory.getLogger(OdlCamelEndpoint.class);

    public OdlCamelEndpoint() {
        LOG.info("OdlCamelEndpoint");
    }

    public OdlCamelEndpoint(String uri, OdlCamelComponent component) {
        super(uri, component);
        LOG.info("OdlCamelEndpoint: uri=" + uri);
    }

    public OdlCamelEndpoint(String endpointUri) {
        super(endpointUri);
        LOG.info("OdlCamelEndpoint: endpointUri=" + endpointUri);
    }

    public Producer createProducer() throws Exception {
        LOG.info("createProducer");
        return new OdlCamelProducer(this);
    }

    public Consumer createConsumer(Processor processor) throws Exception {
        LOG.info("createConsumer");
        return new OdlCamelConsumer(this, processor);
    }

    public boolean isSingleton() {
        return true;
    }
}
