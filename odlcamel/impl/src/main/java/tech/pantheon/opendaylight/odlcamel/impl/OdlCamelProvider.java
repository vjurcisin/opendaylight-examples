/*
 * Copyright © 2016 Pantheon s.r.o and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package tech.pantheon.opendaylight.odlcamel.impl;

import java.util.Collection;
import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;
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
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.odlcamel.rev150105.AppDataNotificationBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.odlcamel.rev150105.OdlcamelService;
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
    private CamelContext context;

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
        try {
            context = new DefaultCamelContext();
            context.addRoutes(new RouteBuilder() {
                public void configure() {
                    LOG.info("OdlCamelProvider: configuring camel route");
                    from("odl://foo")
                            .to("file://tmp/odl-camel-test.txt");
                }
            });
            context.start();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
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
        try {
            context.stop();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSessionInitiated(Broker.ProviderSession providerSession) {
        LOG.info("onSessionInitialized");
        DOMDataBroker ddb = providerSession.getService(DOMDataBroker.class);
        domNotificationPublishService = providerSession.getService(DOMNotificationPublishService.class);
        registerDataListener(ddb);
    }

    @Override
    public Collection<ProviderFunctionality> getProviderFunctionality() {
        return null;
    }

    @Override
    public void onSessionInitiated(Broker.ConsumerSession session) {
        LOG.info("onSessionInitialized");
        DOMNotificationService dns = session.getService(DOMNotificationService.class);
        registerNotificationListener(dns);
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
}