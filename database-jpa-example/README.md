OpenDaylight JPA example
=========================

Simple Java Persistence API test example for OpenDaylight

* mvn clean install -DskipTests
* start karaf ```./karaf/target/assembly/bin/karaf```
* feature:install odl-databasejpa-example
* jpa-example:insert joe doe
* jpa-example:insert jane doe
* jpa-example:getall
* jpa-example:delete 2

Check karaf setup:
* ```service:list DataSource``` - displays used data source
* ```service:list DataSourceFactory``` - displays used data source factories
* ```jdbc:names``` - display registered JDBC data sources
* ```diag``` - check errors after odl-database-example feature installation

