
Create new Carbon MD-SAL project
================================
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
