/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.impl;

import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import org.opendaylight.mdsal.singleton.common.api.ClusterSingletonService;
import org.opendaylight.mdsal.singleton.common.api.ClusterSingletonServiceProvider;
import org.opendaylight.mdsal.singleton.common.api.ClusterSingletonServiceRegistration;
import org.opendaylight.mdsal.singleton.common.api.ServiceGroupIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by gergej on 7.12.2016.
 */
public class ClusterSingleton implements ClusterSingletonService {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterSingleton.class);
    private static final ServiceGroupIdentifier IDENT = ServiceGroupIdentifier.create("ClusterSingleton");

    private final ClusterSingletonServiceProvider clusterSingletonServiceProvider;
    private ClusterSingletonServiceRegistration cssRegistration;
    private AtomicBoolean isLeader;

    public ClusterSingleton(final ClusterSingletonServiceProvider clusterSingletonServiceProvider) {
        this.clusterSingletonServiceProvider = clusterSingletonServiceProvider;
        isLeader = new AtomicBoolean(false);
    }

    @Override
    public void instantiateServiceInstance() {
        LOG.info("We take Leadership");
        isLeader.set(true);
    }

    @Override
    public ListenableFuture<Void> closeServiceInstance() {
        LOG.info("We lost Leadership");
        isLeader.set(false);
        return Futures.immediateFuture(null);
    }

    @Override
    public ServiceGroupIdentifier getIdentifier() {
        return IDENT;
    }

    public void init() {
        LOG.info("ClusterSingleton Initiated: isLeader=" + isLeader.get());
        cssRegistration = clusterSingletonServiceProvider.registerClusterSingletonService(this);

    }

    public void destroy() {
        LOG.info("ClusterSingleton Closed");
        if (cssRegistration != null) {
            try {
                cssRegistration.close();
            } catch (final Exception e) {
                LOG.warn("Unexpected close exception", e);
            }
            cssRegistration = null;
        }
    }

    public boolean isLeader() {
        return isLeader.get();
    }

}
