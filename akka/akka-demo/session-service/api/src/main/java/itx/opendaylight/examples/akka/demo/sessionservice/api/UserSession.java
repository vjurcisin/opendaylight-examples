/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.sessionservice.api;

/**
 * Created by gergej on 4.12.2016.
 */
public interface UserSession extends UserSessionBase {

    public String sendMessage(String message);

}
