OpenDaylight cluster demo
=========================
This demo shows basics of ODL clsuter capabilities. Several examples are implemented.
Main feature ```odl-clusterdemo``` containing implementation of all examples is started automatically on karaf start.

Cluster singleton example (ODL)
-------------------------------
Show if current node is leader. Uses ODL ClusterSingletonServiceProvider.
* start karaf
* try command ```cluster:showodlleader```

Cluster member service
----------------------
Show list of all members in cluster on current node.
* start karaf
* try command ```cluster:showmembers```

Akka Singleton Demo
-------------------
Akk cluster singleton demo implementation.
* start karaf
* try command ```cluster:tellsingleton '<message>'```
* see log files on each cluster node, one of them hosts singleton

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

Task distribution demo
----------------------
Distribute tasks running in separate threads across the cluster nodes.
* start karaf
* try commands
* ```tasks:list``` - list task processing nodes in cluster showing number of tasks on each node
* ```tasks:submit <taskName> <repeatcycles> <delay>``` - submit new task to the cluster

Implementation has following properties:
* Tasks are distributed evenly across the cluster
* New task is started on the node with least amount of running tasks
* New task is tarted only on one node
* If node fails, it is removed from node list
* Tasks running on failed node are lost
* If new node joins cluster it receives information about other nodes
* New node in the cluster is added into node list
* new node in the cluster has 0 tasks running initially
* package: ```itx.opendaylight.examples.cluster.demoakka.impl.tasks```

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
* add ```pub-sub, singleton, singleton-proxy``` section into configuration akka.conf/odl-cluster-data/akka/cluster
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

singleton {
   singleton-name = "the-singleton"
   role = ""
   hand-over-retry-interval = 1s
   min-number-of-hand-over-retries = 10
}

singleton-proxy {
   singleton-name = "the-singleton"
   role = ""
   singleton-identification-interval = 1s
   buffer-size = 1000
}

```
      