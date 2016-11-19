/*
 * Copyright © 2016 Pantheon s.r.o and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package tech.pantheon.opendaylight.odlcamel.impl;

import java.util.Collection;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.NotificationPublishService;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataBroker;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.dom.api.DOMDataBroker;
import org.opendaylight.controller.md.sal.dom.api.DOMNotificationPublishService;
import org.opendaylight.controller.md.sal.dom.api.DOMNotificationService;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker;
import org.opendaylight.controller.sal.binding.api.BindingAwareConsumer;
import org.opendaylight.controller.sal.binding.api.RpcProviderRegistry;
import org.opendaylight.controller.sal.core.api.Broker;
import org.opendaylight.controller.sal.core.api.Consumer;
import org.opendaylight.controller.sal.core.api.Provider;
import org.opendaylight.yangtools.yang.common.QName;
import org.opendaylight.yangtools.yang.data.api.YangInstanceIdentifier;
import org.opendaylight.yangtools.yang.model.api.SchemaPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OdlCamelProvider implements Provider, Consumer, BindingAwareConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OdlCamelProvider.class);
    private static OdlCamelProvider SELF;

    private final DataBroker dataBroker;
    private final Broker brokerImpl;
    private final BindingAwareBroker bindingAwareBroker;
    private DOMNotificationPublishService domNotificationPublishService;
    private final RpcProviderRegistry rpcProviderRegistry;
    private final NotificationPublishService notificationPublishService;
    private DOMNotificationService domNotificationService;
    private DOMDataBroker domDataBroker;

    public OdlCamelProvider(final DataBroker dataBroker, final Broker brokerImpl, 
                            final BindingAwareBroker bindingAwareBroker,
                            final RpcProviderRegistry rpcProviderRegistry,
                            final NotificationPublishService notificationPublishService) {
        LOG.info("OdlCamelProvider");
        SELF = this;
        this.dataBroker = dataBroker;
        this.brokerImpl = brokerImpl;
        this.bindingAwareBroker = bindingAwareBroker;
        this.rpcProviderRegistry = rpcProviderRegistry;
        this.notificationPublishService = notificationPublishService;
    }

    public static OdlCamelProvider getInstance() {
        return SELF;
    }

    /**
     * Method called when the blueprint container is created.
     */
    public void init() {
        LOG.info("OdlCamelProvider: Session Initiated");
        brokerImpl.registerProvider(this);
        brokerImpl.registerConsumer(this);
        bindingAwareBroker.registerConsumer(this);
        rpcProviderRegistry.addRpcImplementation(OdlcamelService.class, new OdlcamelServiceImpl(this));
        LOG.info("OdlCamelProvider: init done");
    }

    /**
     * Method called when the blueprint container is destroyed.
     */
    public void close() {
        LOG.info("OdlCamelProvider Closed");
    }

    @Override
    public void onSessionInitiated(Broker.ProviderSession providerSession) {
        LOG.info("onSessionInitialized");
        domDataBroker = providerSession.getService(DOMDataBroker.class);
        domNotificationPublishService = providerSession.getService(DOMNotificationPublishService.class);
        registerDataListener(domDataBroker);
    }

    @Override
    public Collection<ProviderFunctionality> getProviderFunctionality() {
        return null;
    }

    @Override
    public void onSessionInitiated(Broker.ConsumerSession session) {
        LOG.info("onSessionInitialized");
        domNotificationService = session.getService(DOMNotificationService.class);
        registerNotificationListener(domNotificationService);
    }

    @Override
    public Collection<ConsumerFunctionality> getConsumerFunctionality() {
        return null;
    }

    private void registerDataListener(DOMDataBroker ddb) {
        LOG.info("registerDataListener");
        LogicalDatastoreType type = LogicalDatastoreType.CONFIGURATION;
        YangInstanceIdentifier path = YangInstanceIdentifier.of(QName.create("urn:opendaylight:params:xml:ns:yang:odlcamel", "2015-01-05", "appData"));
        DataChangeListener listener = new DataChangeListener();
        AsyncDataBroker.DataChangeScope scope = AsyncDataBroker.DataChangeScope.SUBTREE;
        ddb.registerDataChangeListener(type, path, listener, scope);
    }

    private void registerNotificationListener(DOMNotificationService domNotificationService) {
        LOG.info("registerNotificationListener");
        QName qname = QName.create("urn:opendaylight:params:xml:ns:yang:odlcamel", "2015-01-05", "appDataNotification");
        NotificationListener notificationListener = new NotificationListener();
        domNotificationService.registerNotificationListener(notificationListener, SchemaPath.create(true, qname));
    }

    protected void triggerNotification() {
        LOG.info("triggerNotification");
        AppDataNotificationBuilder dataNotificationBuilder = new AppDataNotificationBuilder();
        dataNotificationBuilder.setDataNotifStr("xxx");
        notificationPublishService.offerNotification(dataNotificationBuilder.build());
    }

    @Override
    public void onSessionInitialized(BindingAwareBroker.ConsumerContext consumerContext) {
        LOG.info("onSessionInitialized");
    }

    public void registerDataListener(final String namespace, final String revision, final String localName) {
        QName qname = QName.create(namespace, revision, localName);
        LOG.info("registerDataListener: " + qname.toString());
        DataChangeListener listener = new DataChangeListener();
        YangInstanceIdentifier path = YangInstanceIdentifier.of(qname);
        AsyncDataBroker.DataChangeScope scope = AsyncDataBroker.DataChangeScope.SUBTREE;
        LogicalDatastoreType type = LogicalDatastoreType.CONFIGURATION;
        domDataBroker.registerDataChangeListener(type, path, listener, scope);
    }

    public void registerNotificationListener(final String namespace, final String revision, final String localName) {
        QName qname = QName.create(namespace, revision, localName);
        LOG.info("registerNotificationListener: " + qname.toString());
        NotificationListener notificationListener = new NotificationListener();
        domNotificationService.registerNotificationListener(notificationListener, SchemaPath.create(true, qname));
    }

}