/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.mdsalexamples.cli.impl;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.opendaylight.mdsalexamples.cli.api.MdsaldatastoreCliCommands;

public class MdsaldatastoreCliCommandsImpl implements MdsaldatastoreCliCommands {

    private static final Logger LOG = LoggerFactory.getLogger(MdsaldatastoreCliCommandsImpl.class);
    private final DataBroker dataBroker;

    public MdsaldatastoreCliCommandsImpl(final DataBroker db) {
        this.dataBroker = db;
        LOG.info("MdsaldatastoreCliCommandImpl initialized");
    }

    @Override
    public Object testCommand(Object testArgument) {
        return "This is a test implementation of test-command";
    }
}