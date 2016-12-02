
OpenDaylight examples
=====================
This repository contains various example OpenDaylight projects.
Example projects are using:
* [OpenDaylight Carbon SNAPSHOT](https://www.opendaylight.org/)
* [Apache Service Mix 6.1.2](http://servicemix.apache.org/downloads/servicemix-6.1.2.html)
* [Karaf Cellar 3.0.3](http://karaf.apache.org/download.html#cellar-403)

Before you begin
----------------
Based on [this guide](https://wiki.opendaylight.org/view/OpenDaylight_Controller:MD-SAL:Startup_Project_Archetype)
Edit your ~/.m2/settings.xml file as described [here](https://wiki.opendaylight.org/view/GettingStarted:Development_Environment_Setup#Edit_your_.7E.2F.m2.2Fsettings.xml) 

Create your own Carbon MD-SAL project
-------------------------------------
```
mvn archetype:generate -DarchetypeGroupId=org.opendaylight.controller -DarchetypeArtifactId=opendaylight-startup-archetype \
-DarchetypeRepository=http://nexus.opendaylight.org/content/repositories/opendaylight.snapshot/ \
-DarchetypeCatalog=http://nexus.opendaylight.org/content/repositories/opendaylight.snapshot/archetype-catalog.xml \
-DarchetypeVersion=1.3.0-SNAPSHOT
```
```
'groupId': : org.opendaylight.mdsalexamples
'artifactId': : mdsalexample
'package':  org.opendaylight.mdsalexamples: :
'classPrefix':  ${artifactId.substring(0,1).toUpperCase()}${artifactId.substring(1)}
'copyright': : Yoyodyne, Inc. 
```


