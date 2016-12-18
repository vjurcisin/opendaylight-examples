/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.genericpubsub;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;
import akka.japi.Creator;
import org.opendaylight.controller.cluster.ActorSystemProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by gergej on 17.12.2016.
 */
public class GenericPubSubService {

    private static final Logger LOG = LoggerFactory.getLogger(GenericPubSubService.class);

    private ActorSystemProvider actorSystemProvider;
    private ActorRef mediator;

    public GenericPubSubService(ActorSystemProvider actorSystemProvider) {
        LOG.info("GenericPubSubService");
        this.actorSystemProvider = actorSystemProvider;
    }

    public void init() {
        LOG.info("init");
        mediator = DistributedPubSub.get(actorSystemProvider.getActorSystem()).mediator();
    }

    public void destroy() {
        LOG.info("destroy");
    }

    public ActorRef subscribe(Creator<? extends UntypedActor> creator, String topicName) {
        LOG.info("subscribe: " + topicName);
        ActorRef actorRef = actorSystemProvider.getActorSystem().actorOf(Props.create(creator));
        mediator.tell(new DistributedPubSubMediator.Subscribe(topicName, actorRef), actorRef);
        return actorRef;
    }
    public ActorRef subscribe(Creator<? extends UntypedActor> creator, String actorName, String topicName) {
        LOG.info("subscribe: " + actorName + " " + topicName);
        ActorRef actorRef = actorSystemProvider.getActorSystem().actorOf(Props.create(creator), actorName);
        mediator.tell(new DistributedPubSubMediator.Subscribe(topicName, actorRef), actorRef);
        return actorRef;
    }

    public void subscribe(ActorRef actorRef, String topicName) {
        LOG.info("subscribe: " + topicName);
        mediator.tell(new DistributedPubSubMediator.Subscribe(topicName, actorRef), actorRef);
    }

    public void unSubscribe(ActorRef actorRef, String topicName) {
        LOG.info("unSubscribe: " + topicName);
        mediator.tell(new DistributedPubSubMediator.Unsubscribe(topicName, actorRef), actorRef);
    }

    public void publish(String topicName, Object message) {
        mediator.tell(new DistributedPubSubMediator.Publish(topicName, message), null);
    }

    public void publish(String topicName, Object message, ActorRef sender) {
        mediator.tell(new DistributedPubSubMediator.Publish(topicName, message), sender);
    }

}
