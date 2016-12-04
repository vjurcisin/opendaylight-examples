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
public class MessageResponse extends MessageBase {

    public static final String TYPE = "messageresponse";

    private String sessionId;
    private String clientId;
    private Status status;
    private String message;

    public MessageResponse() {
        super(TYPE);
    }

    public MessageResponse(String type, String sessionId, String clientId, Status status, String message) {
        super(TYPE);
        this.sessionId = sessionId;
        this.clientId = clientId;
        this.status = status;
        this.message = message;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
