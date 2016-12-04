OpenDaylight akka demo example
==============================

* mvn clean install -DskipTests
* start karaf ```./karaf/target/assembly/bin/karaf```
* feature:install akkademo-eventbus
* feature:install session-service
* feature:install web-ui
* goto URL http://localhost:8181/webdata/index.html
* follow onscreen instructions  

Check karaf setup:
* ```diag``` - check errors after odl-database-example feature installation
* ```http:list``` - check deployed servlets

