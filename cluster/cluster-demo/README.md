OpenDaylight cluster demo
=========================
This demo shows basics of ODL clsuter capabilities. Several examples are implemented.
Main feature ```odl-clusterdemo``` containing implementation of all examples is started automatically on karaf start.

Cluster singleton example (ODL)
-------------------------------
Show if current node is leader. Uses ODL ClusterSingletonServiceProvider.
* start karaf
* try command ```cluster:showleader```

Cluster member service
----------------------
Show list of all members in cluster on current node.
* start karaf
* try command ```cluster:showmembers```

Distributed publish subscribe example
-------------------------------------
Dynamically create and remove subcribers on cluster nodes. Send mesages to distributed topics.
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

Deployment notes:
-----------------
This demo runs on single ODL node or in cluster of ODL nodes.
Automatic cluster deployment script ```clsuter-deploy.sh``` is in ```karaf/src/site``` directory.
Ideally this demo should be deployed as 3-node ODL cluster setup. 

Single Node deployment:
-----------------------
Do not forget to modify ```configuration/initial/akka.conf```
* add ```pub-sub``` section into configuration akka.conf/odl-cluster-data/akka/cluster
* add ```extensions = ["akka.cluster.pubsub.DistributedPubSub"]``` into akka.conf/odl-cluster-data/akka
* check akka giude for [pubsub](http://doc.akka.io/docs/akka/2.4/java/distributed-pub-sub.html)

```
pub-sub {
   name = distributedPubSubMediator
   role = ""
   routing-logic = random
   gossip-interval = 1s
   removed-time-to-live = 120s
   max-delta-elements = 3000
   use-dispatcher = ""
}
```
