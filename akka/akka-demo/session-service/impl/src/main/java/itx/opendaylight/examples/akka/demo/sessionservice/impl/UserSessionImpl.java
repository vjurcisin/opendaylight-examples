/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.sessionservice.impl;

import itx.opendaylight.examples.akka.demo.sessionservice.api.UserSession;

/**
 * Created by gergej on 4.12.2016.
 */
public class UserSessionImpl extends UserSessionBaseImpl implements UserSession {

    public UserSessionImpl(String id, String clientId) {
        super(id, clientId);
    }

    @Override
    public String sendMessage(String message) {
        return "Echo: " + message;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other);
    }

}
