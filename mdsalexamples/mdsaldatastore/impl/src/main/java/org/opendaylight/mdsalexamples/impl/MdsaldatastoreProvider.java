/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsalexamples.impl;

import java.util.Collection;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareConsumer;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.controller.sal.core.api.Broker;
import org.opendaylight.controller.sal.core.api.Consumer;
import org.opendaylight.controller.sal.core.api.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MdsaldatastoreProvider implements Provider, Consumer, BindingAwareConsumer, BindingAwareProvider {

    private static final Logger LOG = LoggerFactory.getLogger(MdsaldatastoreProvider.class);

    private final DataBroker dataBroker;
    private final Broker brokerImpl;
    private final BindingAwareBroker bindingAwareBroker;
    private final RpcProviderRegistry rpcProviderRegistry;
    private final NotificationPublishService notificationPublishService;

    public MdsaldatastoreProvider(final DataBroker dataBroker, final Broker brokerImpl,
                                  final BindingAwareBroker bindingAwareBroker,
                                  final RpcProviderRegistry rpcProviderRegistry,
                                  final NotificationPublishService notificationPublishService) {
        this.dataBroker = dataBroker;
        this.brokerImpl = brokerImpl;
        this.bindingAwareBroker = bindingAwareBroker;
        this.rpcProviderRegistry = rpcProviderRegistry;
        this.notificationPublishService = notificationPublishService;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {
        LOG.info("MdsaldatastoreProvider Session Initiated");
        brokerImpl.registerProvider(this);
        brokerImpl.registerConsumer(this);
        bindingAwareBroker.registerConsumer(this);
        bindingAwareBroker.registerProvider(this);
    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("MdsaldatastoreProvider Closed");
    }

    @Override
    public void onSessionInitialized(BindingAwareBroker.ConsumerContext consumerContext) {
        LOG.info("onSessionInitialized");
    }

    @Override
    public void onSessionInitiated(Broker.ConsumerSession consumerSession) {
        LOG.info("onSessionInitialized");
    }

    @Override
    public Collection<ConsumerFunctionality> getConsumerFunctionality() {
        return null;
    }

    @Override
    public void onSessionInitiated(Broker.ProviderSession providerSession) {
        LOG.info("onSessionInitialized");
    }

    @Override
    public Collection<ProviderFunctionality> getProviderFunctionality() {
        return null;
    }

    @Override
    public void onSessionInitiated(BindingAwareBroker.ProviderContext providerContext) {
        LOG.info("onSessionInitialized");
    }
}