ODL Cluster control script
==========================

```cluster-deploy.sh``` script is designed to cotrol cluster od ODL nodes.

Script requires following setup:
* All controlled nodes must be on same network segment
* Installed ```sshpass``` locally (```apt-get install sshpass```)  
* Each controlled node must have setup described below

Node setup
----------
* Installed oracle JVM, so ```java``` command is avaliable for root 
* Created empty directory ```/opt/karaf```
* Enable ssh root login using password
* Set ```JAVA_HOME``` env. variable for root

Before runnig script
--------------------
Do not forget to build project locally using
```mvn clean install -DskipTests```

```cluster-deploy.sh``` usage
-----------------------------

```cluster-deploy.sh [function] [node index]```
* function - is one of list|install|start|stop|status|shutdown
* node index - is optional ordinal of controlled node
  if node index is not provided, function is applied on all nodes

