/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demo.cli.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import itx.opendaylight.examples.cluster.demo.cli.api.ClusterdemoCliCommands;

public class ClusterdemoCliCommandsImpl implements ClusterdemoCliCommands {

    private static final Logger LOG = LoggerFactory.getLogger(ClusterdemoCliCommandsImpl.class);
    private final DataBroker dataBroker;

    public ClusterdemoCliCommandsImpl(final DataBroker db) {
        this.dataBroker = db;
        LOG.info("ClusterdemoCliCommandImpl initialized");
    }

    @Override
    public Object testCommand(Object testArgument) {
        return "This is a test implementation of test-command";
    }
}