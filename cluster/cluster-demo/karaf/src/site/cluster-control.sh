#! /bin/bash

#***************************************
# GLOBAL VARIABLES SETUP
#***************************************
if [ "${ODL_PROJECT_PROFILE}xxx" == "xxx" ]; then
   echo "loading default profile"
   . default-profile.sh
else
   echo "loading profile ${ODL_PROJECT_PROFILE}"
   . ${ODL_PROJECT_PROFILE}
fi

COMMAND=$1
SERVER_INDEX=$2
SELECTED_IP=''

#echo "SERVER_PASSWD=${SERVER_PASSWD}"
#echo "SERVER_USERNAME=${SERVER_USERNAME}"
#echo "SERVERS_LIST=${SERVERS_LIST}"
#exit 0

#***************************************
# FUNCTIONS
#***************************************

function listAll {
    COUNTER=1
    for SERVER in ${SERVERS_LIST}; do
        echo "Server [${COUNTER}]: ${SERVER}"
        let COUNTER=COUNTER+1
    done
}

function selectOne {
    echo "SelectOne: ${SERVER_INDEX}"
    COUNTER=1
    for SERVER in ${SERVERS_LIST}; do
        if [ "${COUNTER}xxx" == "${SERVER_INDEX}xxx" ]; then
           SELECTED_IP="${SERVER}"
        fi
        let COUNTER=COUNTER+1
    done
}

function installOne {
    SERVER=$1
    SERVER_ORDINAL=$2
    echo "Installing server [${SERVER_ORDINAL}] ${SERVER}"
    sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} hostname
    if [ $? == 0 ]; then
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} pkill java
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} rm -rf /opt/${KARAF_FOLDER_NAME}
       sshpass -p ${SERVER_PASSWD} scp ${JENKINS_KARAF_ZIP_PATH} ${SERVER_USERNAME}@${SERVER}:/opt/
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} tar zxvf /opt/${KARAF_ZIP_NAME} -C /opt/
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} mkdir -p /opt/${KARAF_FOLDER_NAME}/configuration/initial/
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} rm -rf /opt/${KARAF_FOLDER_NAME}/configuration/initial/*
       sshpass -p ${SERVER_PASSWD} scp -r node-0${SERVER_ORDINAL}/* ${SERVER_USERNAME}@${SERVER}:/opt/${KARAF_FOLDER_NAME}/configuration/initial/
       sshpass -p ${SERVER_PASSWD} scp -r node-common/* ${SERVER_USERNAME}@${SERVER}:/opt/${KARAF_FOLDER_NAME}/configuration/initial/
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} rm -rf /opt/${KARAF_FOLDER_NAME}/data/*
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} mkdir -p /opt/${KARAF_FOLDER_NAME}/data/tmp/
       echo "done"
    else
       echo "ERROR: server ${SERVER} is offline"
    fi
}

function installAll {
    COUNTER=1
    for SERVER in ${SERVERS_LIST}; do
        installOne ${SERVER} ${COUNTER}
        let COUNTER=COUNTER+1
    done
}

function startOne {
    SERVER=$1
    echo "Starting server ${SERVER}"
    sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} hostname
    if [ $? == 0 ]; then
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} /opt/${KARAF_FOLDER_NAME}/bin/start
       echo "done"
    else
       echo "ERROR: server ${SERVER} is offline"
    fi
}

function startAll {
    for SERVER in ${SERVERS_LIST}; do
        startOne ${SERVER}
    done
}

function stopOne {
    SERVER=$1
    sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} hostname
    if [ $? == 0 ]; then
       echo "Stopping server ${SERVER}"
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} /opt/${KARAF_FOLDER_NAME}/bin/stop
       echo "done"
    else
       echo "ERROR: server ${SERVER} is offline"
    fi
}

function stopAll {
    for SERVER in ${SERVERS_LIST}; do
        stopOne ${SERVER}
    done
}

function statusOne {
    SERVER=$1
    sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} hostname
    if [ $? == 0 ]; then
       echo "Checking server ${SERVER}"
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} /opt/${KARAF_FOLDER_NAME}/bin/status
       echo "done"
    else
       echo "ERROR: server ${SERVER} is offline"
    fi
}

function statusAll {
    for SERVER in ${SERVERS_LIST}; do
        statusOne ${SERVER}
    done
}

function shutdownAll {
    for SERVER in ${SERVERS_LIST}; do
        shutdownOne ${SERVER}
    done
}

function shutdownOne {
    SERVER=$1
    sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} hostname
    if [ $? == 0 ]; then
       echo "Shutting down server ${SERVER}"
       sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SERVER} shutdown -h now
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
        installOne ${SELECTED_IP} ${SERVER_INDEX}
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
