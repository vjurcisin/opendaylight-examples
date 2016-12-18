/*
 * Copyright © 2016 Yoyodyne, Inc.  and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package itx.opendaylight.examples.cluster.demoakka.impl.pubsub;

/**
 * Created by gergej on 8.12.2016.
 */
public class ActorInfo {

    private String name;
    private String topicName;

    public ActorInfo(String name, String topicName) {
        this.name = name;
        this.topicName = topicName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    @Override
    public String toString() {
        return "ACTOR: " + name + " topic=" + topicName;
    }

}
