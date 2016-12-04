/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.webui.web;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.HttpService;
import org.osgi.service.http.NamespaceException;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 4.12.2016.
 */
public class WebBundleActivator implements BundleActivator, ServiceTrackerCustomizer<HttpService, HttpService> {

    private static final Logger LOG = LoggerFactory.getLogger(WebBundleActivator.class);

    private BundleContext bundleContext;
    private ServiceTracker<HttpService, HttpService> tracker;

    @Override
    public void start(BundleContext context) throws Exception {
        LOG.info("start ...");
        bundleContext = context;
        tracker = new ServiceTracker<>(context, HttpService.class, this);
        tracker.open();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        LOG.info("stop ...");
        tracker.close();
    }

    @Override
    public HttpService addingService(ServiceReference<HttpService> reference) {
        LOG.info("addingService ...");
        final HttpService httpService = (HttpService) bundleContext
                .getService(reference);
        if (httpService != null) {
            final HttpContext httpContext = httpService.createDefaultHttpContext();
            try {
                httpService.registerResources("/webdata", "/webdata", httpContext);
            } catch (NamespaceException e) {
                LOG.error("NamespaceException", e);

            }
        }
        return httpService;
    }

    @Override
    public void modifiedService(ServiceReference<HttpService> reference, HttpService service) {
        LOG.info("modifiedService ...");
    }

    @Override
    public void removedService(ServiceReference<HttpService> reference, HttpService service) {
        LOG.info("removedService ...");
    }

}
