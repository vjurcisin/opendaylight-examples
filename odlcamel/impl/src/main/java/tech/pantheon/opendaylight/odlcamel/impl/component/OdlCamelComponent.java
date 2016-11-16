/*
 * Copyright © 2016 Pantheon s.r.o and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package tech.pantheon.opendaylight.odlcamel.impl.component;

import java.util.Map;

import org.apache.camel.Endpoint;
import org.apache.camel.impl.DefaultComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the component that manages {@link OdlCamelEndpoint}.
 */
public class OdlCamelComponent extends DefaultComponent {

    private static final Logger LOG = LoggerFactory.getLogger(OdlCamelComponent.class);

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        LOG.info("createEndpoint: ", uri, remaining);
        Endpoint endpoint = new OdlCamelEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
