#!/bin/bash

SESSION=$USER
START_DELAY=1

tmux -2 new-session -d -s $SESSION
tmux new-window -t $SESSION:1 -n 'ODLCluster'

tmux select-pane -t 0
tmux split-window -v -p 66
tmux select-pane -t 1
tmux split-window -v -p 50
tmux select-pane -t 0
tmux split-window -h -p 66
tmux select-pane -t 2
tmux split-window -h -p 66
tmux select-pane -t 4
tmux split-window -h -p 66

if [ "${ODL_PROJECT_PROFILE}xxx" == "xxx" ]; then
   echo "loading default profile"
   . default-profile.sh
else
   echo "loading profile ${ODL_PROJECT_PROFILE}"
   . ${ODL_PROJECT_PROFILE}
fi

SELECTED_IP=''

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

SERVER_INDEX=1
PANE_COUNTER=0
for SERVER in ${SERVERS_LIST}; do

    selectOne ${SERVER_INDEX}

    PANE1_INDEX=${PANE_COUNTER}
    let PANE2_INDEX=${PANE_COUNTER}+1
    echo "Server [${SERVER_INDEX}]: ${SELECTED_IP} ${PANE1_INDEX} ${PANE2_INDEX}"

    tmux select-pane -t ${PANE1_INDEX}
    tmux send-keys "sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SELECTED_IP}" C-m
    sleep ${START_DELAY}
    tmux send-keys "/opt/karaf/bin/karaf clean" C-m

    tmux select-pane -t ${PANE2_INDEX}
    tmux send-keys "sshpass -p ${SERVER_PASSWD} ssh ${SERVER_USERNAME}@${SELECTED_IP}" C-m
    sleep ${START_DELAY}
    tmux send-keys "tail -f /opt/karaf/data/log/karaf.log" C-m

    let SERVER_INDEX=SERVER_INDEX+1
    let PANE_COUNTER=PANE_COUNTER+2
done

tmux select-window -t $SESSION:1
tmux -2 attach-session -t $SESSION

