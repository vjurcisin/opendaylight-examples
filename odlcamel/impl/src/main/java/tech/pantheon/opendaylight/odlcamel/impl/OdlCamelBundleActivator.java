/*
 * Copyright © 2016 Pantheon s.r.o and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package tech.pantheon.opendaylight.odlcamel.impl;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by juraj on 8.11.2016.
 */
public class OdlCamelBundleActivator implements BundleActivator {

    private static final Logger LOG = LoggerFactory.getLogger(OdlCamelBundleActivator.class);

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        LOG.info("OdlCamelBundleActivator: start");
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        LOG.info("OdlCamelBundleActivator: stop");
    }
}
