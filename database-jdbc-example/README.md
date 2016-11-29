OpenDaylight JDBC example
=========================

Simple JDBC test for OpenDaylight

* mvn clean install -DskipTests
* start karaf
* feature:install odl-database-example
* jdbc-example-insert joe
* jdbc-example-insert jane
* jdbc-example-get

