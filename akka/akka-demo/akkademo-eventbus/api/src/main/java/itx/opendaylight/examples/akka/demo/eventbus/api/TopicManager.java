/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.eventbus.api;

import akka.actor.ActorRef;
import akka.actor.Props;

/**
 * @author gergej
 */
public interface TopicManager {

    public void subscribe(ActorRef actorRef, String topicId);

    public void unsubscribe(ActorRef actorRef);

    public void publish(MessageBase message);

    public ActorRef createActor(Props props, String name);

    public ActorRef createActor(Props props);

}
