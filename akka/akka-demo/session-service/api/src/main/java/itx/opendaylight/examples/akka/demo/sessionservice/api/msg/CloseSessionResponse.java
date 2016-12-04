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
public class CloseSessionResponse extends MessageBase {

    private String sessionId;
    private String clientId;
    private Status status;

}
