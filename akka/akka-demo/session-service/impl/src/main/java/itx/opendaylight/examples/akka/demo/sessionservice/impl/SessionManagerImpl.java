/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.sessionservice.impl;

import itx.opendaylight.examples.akka.demo.sessionservice.api.SessionManagementException;
import itx.opendaylight.examples.akka.demo.sessionservice.api.SessionManager;
import itx.opendaylight.examples.akka.demo.sessionservice.api.UserSession;
import itx.opendaylight.examples.akka.demo.sessionservice.api.UserSessionBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Created by gergej on 4.12.2016.
 */
public class SessionManagerImpl implements SessionManager {

    private static final Logger LOG = LoggerFactory.getLogger(SessionManagerImpl.class);

    private Map<String, UserSession> sessions;

    public SessionManagerImpl() {
        LOG.info("init ...");
        sessions = new HashMap<>();
    }

    @Override
    public synchronized String createSession(String clientId) {
        String sessionId = UUID.randomUUID().toString();
        UserSession session = new UserSessionImpl(sessionId, clientId);
        sessions.put(sessionId, session);
        return sessionId;
    }

    @Override
    public synchronized String sendMessage(String sessionId, String message) throws SessionManagementException {
        UserSession session = sessions.get(sessionId);
        if (session != null) {
            return session.sendMessage(message);
        }
        throw new SessionManagementException("Session not found");
    }

    @Override
    public synchronized void closeSession(String sessionId) throws SessionManagementException {
        UserSession session = sessions.remove(sessionId);
        if (session == null) {
            throw new SessionManagementException("Session not found");
        }
    }

    @Override
    public synchronized List<UserSessionBase> getSessions() {
        List<UserSessionBase> result = new ArrayList<>();
        sessions.forEach( (id, s) -> { result.add(new UserSessionBaseImpl(s.getId(), s.getClientId() )); });
        return result;
    }

}
