/*
 * Copyright © 2016 itx and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.akka.demo.eventbus.api;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.japi.Creator;

/**
 * @author gergej
 */
public interface TopicManager {

    public static final String TOPIC_SEPARATOR = ".";

    public ActorRef subscribe(Creator<? extends UntypedActor> actorCreator, String topicId, String actorName);

    public void unsubscribe(ActorRef actorRef);

    public void publish(MessageBase message);

}
