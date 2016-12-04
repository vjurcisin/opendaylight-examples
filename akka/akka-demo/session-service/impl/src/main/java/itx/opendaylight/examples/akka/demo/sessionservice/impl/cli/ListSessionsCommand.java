/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.sessionservice.impl.cli;

import itx.opendaylight.examples.akka.demo.sessionservice.api.SessionManager;
import itx.opendaylight.examples.akka.demo.sessionservice.api.UserSessionBase;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import java.util.List;

/**
 * Created by gergej on 4.12.2016.
 */
@Command(scope = "sessionmanager", name = "list", description = "list all available sessions")
public class ListSessionsCommand extends OsgiCommandSupport {

    private SessionManager sessionManager;

    public ListSessionsCommand(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    protected Object doExecute() throws Exception {
        List<UserSessionBase> result = sessionManager.getSessions();
        StringBuffer sb = new StringBuffer();
        sb.append("sessions: [");
        sb.append(result.size());
        sb.append("]\n");
        result.forEach( s -> { sb.append(s.toString()); sb.append("\n"); } );
        sb.append("OK\n");
        return sb.toString();
    }

}
