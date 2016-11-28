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
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tech.pantheon.opendaylight.odlcamel.impl.OdlCamelProvider;

/**
 * Represents the component that manages {@link OdlCamelEndpoint}.
 */
public class OdlCamelComponent extends DefaultComponent {

    private static final Logger LOG = LoggerFactory.getLogger(OdlCamelComponent.class);

    public OdlCamelComponent() {
        LOG.info("OdlCamelComponent initializing");
        /*
        try {
            BundleContext ctx = FrameworkUtil.getBundle(getClass()).getBundleContext();
            ServiceReference<?>[] refs = ctx.getServiceReferences(OdlCamelProvider.class.getName(), null);
            OdlCamelProvider provider = null;
            for (ServiceReference ref : refs) {
                if (ctx.getService(ref) instanceof OdlCamelProvider) {
                    LOG.info("found OdlCamelProvider service");
                    provider = (OdlCamelProvider) ctx.getService(ref);
                }
            }
            LOG.info("provider: " + (provider != null ));
        } catch (InvalidSyntaxException e) {
            LOG.error("OdlCamelComponent: init error");
            e.printStackTrace();
        }
        */
        LOG.info("provider: " + (OdlCamelProvider.getInstance() != null ));
    }

    protected Endpoint createEndpoint(String uri, String remaining, Map<String, Object> parameters) throws Exception {
        LOG.info("createEndpoint: ", uri, remaining);
        Endpoint endpoint = new OdlCamelEndpoint(uri, this);
        setProperties(endpoint, parameters);
        return endpoint;
    }
}
