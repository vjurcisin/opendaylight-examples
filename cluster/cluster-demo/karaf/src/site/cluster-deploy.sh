#! /bin/bash

#***************************************
# GLOBAL VARIABLES SETUP
#***************************************
USERNAME=root
PASSWD=gergej
ROOT_DIR=/opt/
COMMAND=$1
SERVER_INDEX=$2
if [ "${SERVERS}xxx" == "xxx" ]; then
   SERVERS='192.168.56.101 192.168.56.102 192.168.56.103'
fi
SELECTED_IP=''

#***************************************
# FUNCTIONS
#***************************************

function listAll {
    COUNTER=1
    for SERVER in ${SERVERS}; do
        echo "Server [${COUNTER}]: ${SERVER}"
        let COUNTER=COUNTER+1
    done
}

function selectOne {
    echo "SelectOne: ${SERVER_INDEX}"
    COUNTER=1
    for SERVER in ${SERVERS}; do
        if [ "${COUNTER}xxx" == "${SERVER_INDEX}xxx" ]; then
           SELECTED_IP="${SERVER}"
        fi
        let COUNTER=COUNTER+1
    done
}

function installOne {
    SERVER=$1
    echo "Installing server ${SERVER}"
    sshpass -p gergej ssh root@${SERVER} hostname
    if [ $? == 0 ]; then
       sshpass -p gergej ssh root@${SERVER} rm -rf /opt/karaf/*
       sshpass -p gergej scp -r ../../target/assembly/* root@${SERVER}:/opt/karaf/
       echo "done"
    else
       echo "ERROR: server ${SERVER} is offline"
    fi
}

function installAll {
    for SERVER in ${SERVERS}; do
        installOne ${SERVER}
    done
}

function startOne {
    SERVER=$1
    echo "Starting server ${SERVER}"
    sshpass -p gergej ssh root@${SERVER} hostname
    if [ $? == 0 ]; then
       sshpass -p gergej ssh root@${SERVER} /opt/karaf/bin/start
       echo "done"
    else
       echo "ERROR: server ${SERVER} is offline"
    fi
}

function startAll {
    for SERVER in ${SERVERS}; do
        startOne ${SERVER}
    done
}

function stopOne {
    SERVER=$1
    sshpass -p gergej ssh root@${SERVER} hostname
    if [ $? == 0 ]; then
       echo "Stopping server ${SERVER}"
       sshpass -p gergej ssh root@${SERVER} /opt/karaf/bin/stop
       echo "done"
    else
       echo "ERROR: server ${SERVER} is offline"
    fi
}

function stopAll {
    for SERVER in ${SERVERS}; do
        stopOne ${SERVER}
    done
}

function statusOne {
    SERVER=$1
    sshpass -p gergej ssh root@${SERVER} hostname
    if [ $? == 0 ]; then
       echo "Checking server ${SERVER}"
       sshpass -p gergej ssh root@${SERVER} /opt/karaf/bin/status
       echo "done"
    else
       echo "ERROR: server ${SERVER} is offline"
    fi
}

function statusAll {
    for SERVER in ${SERVERS}; do
        statusOne ${SERVER}
    done
}

function shutdownAll {
    for SERVER in ${SERVERS}; do
        shutdownOne ${SERVER}
    done
}

function shutdownOne {
    SERVER=$1
    sshpass -p gergej ssh root@${SERVER} hostname
    if [ $? == 0 ]; then
       echo "Shutting down server ${SERVER}"
       sshpass -p gergej ssh root@${SERVER} shutdown -h now
       echo "done"
    else
       echo "ERROR: server ${SERVER} is offline"
    fi
}

#*******************************
# MAIN
#*******************************

case "${COMMAND}" in
  list)
     if [ "${SERVER_INDEX}xxx" == "xxx" ]; then
        listAll
     else
        selectOne ${SERVER_INDEX}
        echo "Selected server: ${SELECTED_IP}"
     fi
  ;;
  install)
     if [ "${SERVER_INDEX}xxx" == "xxx" ]; then
        installAll
     else
        selectOne ${SERVER_INDEX}
        installOne ${SELECTED_IP}
     fi
  ;;
  start)
     if [ "${SERVER_INDEX}xxx" == "xxx" ]; then
        startAll
     else
        selectOne ${SERVER_INDEX}
        startOne ${SELECTED_IP}
     fi
  ;;
  stop)
     if [ "${SERVER_INDEX}xxx" == "xxx" ]; then
        stopAll
     else
        selectOne ${SERVER_INDEX}
        stopOne ${SELECTED_IP}
     fi
  ;;
  status)
     if [ "${SERVER_INDEX}xxx" == "xxx" ]; then
        statusAll
     else
        selectOne ${SERVER_INDEX}
        statusOne ${SELECTED_IP}
     fi
  ;;
  shutdown)
     if [ "${SERVER_INDEX}xxx" == "xxx" ]; then
        shutdownAll
     else
        selectOne ${SERVER_INDEX}
        shutdownOne ${SELECTED_IP}
     fi
  ;;
  *)
     echo $"Usage: $0 {list|install|start|stop|status|shutdown}"
     exit 1
  ;;
esac
