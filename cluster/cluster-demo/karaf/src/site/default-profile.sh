#!/bin/bash

echo "default profile"
export SERVER_USERNAME=root
export SERVER_PASSWD=****
export SERVERS_LIST='192.168.56.101 192.168.56.102 192.168.56.103'
export KARAF_ZIP_NAME=snmp-southbound-karaf-0.1.0-SNAPSHOT.tar.gz
export KARAF_FOLDER_NAME=snmp-southbound-karaf-0.1.0-SNAPSHOT
export JENKINS_KARAF_ZIP_PATH=/home/jenkins/snmp/${KARAF_ZIP_NAME}
