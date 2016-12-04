/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.sessionservice.api.msg;

import itx.opendaylight.examples.akka.demo.eventbus.api.MessageBase;

/**
 * Created by gergej on 4.12.2016.
 */
public class CloseSessionRequest extends MessageBase {

    public static final String TYPE = "closesessionrequest";

    private String sessionId;

    public CloseSessionRequest() {
        super(TYPE);
    }

    public CloseSessionRequest(String sessionId) {
        super(TYPE);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

}
