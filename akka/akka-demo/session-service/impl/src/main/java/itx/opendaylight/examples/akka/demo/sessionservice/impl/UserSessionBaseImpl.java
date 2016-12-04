/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.sessionservice.impl;

import itx.opendaylight.examples.akka.demo.sessionservice.api.UserSessionBase;

/**
 * Created by gergej on 4.12.2016.
 */
public class UserSessionBaseImpl implements UserSessionBase {

    private String id;
    private String clientId;

    public UserSessionBaseImpl(String id, String clientId) {
        this.id = id;
        this.clientId = clientId;
    }

    public UserSessionBaseImpl(UserSessionBaseImpl session) {
        this.id = session.id;
        this.clientId = session.clientId;
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getClientId() {
        return null;
    }

    @Override
    public String toString() {
        return "SESSION:" + id + ":" + clientId;
    }

    @Override
    public boolean equals(Object other) {
        if (other != null && other instanceof UserSessionBaseImpl) {
            UserSessionBaseImpl otherSession = (UserSessionBaseImpl)other;
            return id.equals(otherSession.id);
        }
        return false;
    }

}
