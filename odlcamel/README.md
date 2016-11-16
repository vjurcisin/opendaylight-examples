Compile
=======
mvn clean install -DskipTests

Run
===
./karaf/target/assembly/bin/karaf
feature:install odl-odlcamel 
feature:install client-odlcamel-a 
feature:install client-odlcamel-b
camel:context-list
camel:route-list 
camel:endpoint-list blueprintAContext
camel:endpoint-list blueprintBContext

Test
====
dbd


