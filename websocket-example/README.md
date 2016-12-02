OpenDaylight WebSocket example
==============================

Simple WebSocket test example for OpenDaylight

* mvn clean install -DskipTests
* start karaf ```./karaf/target/assembly/bin/karaf```
* feature:install odl-websocket-example
* goto URL http://localhost:8181/webdata/index.html
* follow onscreen instructions  

Check karaf setup:
* ```diag``` - check errors after odl-database-example feature installation
* ```http:list``` - check deployed servlets
