OpenDaylight JDBC example
=========================

Simple JDBC test example for OpenDaylight

* mvn clean install -DskipTests
* start karaf ```./karaf/target/assembly/bin/karaf```
* feature:install odl-database-example
* jdbc-example:insert joe doe
* jdbc-example:insert jane doe
* jdbc-example:getall
* jdbc-example:delete 2

Check karaf setup:
* ```service:list DataSource``` - displays used data source
* ```diag``` - check errors after odl-database-example feature installation

