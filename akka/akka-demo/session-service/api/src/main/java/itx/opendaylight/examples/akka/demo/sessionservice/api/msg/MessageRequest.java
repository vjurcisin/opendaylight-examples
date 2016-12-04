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
public class MessageRequest extends MessageBase {

    public static final String TYPE = "messagerequest";

    private String sessionId;
    private String message;

    public MessageRequest() {
        super(TYPE);
    }

    public MessageRequest(String type, String sessionId, String message) {
        super(TYPE);
        this.sessionId = sessionId;
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
