OpenDaylight cluster demo
=========================
This demo implements following functionality:

Cluster singleton example
-------------------------
* start karaf
* try command ```cluster:showleader```

Distributed publish subscribe example
-------------------------------------
* start karaf
* try commands
* ```pubsub:subscribe <topicName>```
* ```pubsub:unsubscribe <actorName>```
* ```pubsub:list```
* ```pubsub:publish <topicName> <message>```
* karaf log file shows who consumed message

ODL APIs used:
--------------
* ```org.opendaylight.mdsal.singleton.common.api.ClusterSingletonServiceProvider```
* ```org.opendaylight.controller.cluster.ActorSystemProvider```
