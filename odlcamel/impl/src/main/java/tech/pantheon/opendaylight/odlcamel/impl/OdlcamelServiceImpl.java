/*
 * Copyright © 2016 Pantheon s.r.o and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package tech.pantheon.opendaylight.odlcamel.impl;

import java.util.concurrent.Future;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.odlcamel.rev150105.OdlcamelService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.odlcamel.rev150105.TriggerNotificationInput;
import org.opendaylight.yang.gen.v1.urn.opendaylight.params.xml.ns.yang.odlcamel.rev150105.TriggerNotificationOutput;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by juraj on 10.11.2016.
 */
public class OdlcamelServiceImpl implements OdlcamelService {

    private static final Logger LOG = LoggerFactory.getLogger(OdlcamelServiceImpl.class);

    private OdlCamelProvider odlCamelProvider;

    public OdlcamelServiceImpl(OdlCamelProvider odlCamelProvider) {
        this.odlCamelProvider = odlCamelProvider;
    }

    @Override
    public Future<RpcResult<TriggerNotificationOutput>> triggerNotification(TriggerNotificationInput input) {
        LOG.info("triggerNotification RPC");
        odlCamelProvider.triggerNotification();
        return null;
    }

}
