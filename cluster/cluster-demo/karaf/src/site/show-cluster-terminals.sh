#!/bin/bash

SESSION=$USER

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

tmux select-pane -t 0
tmux send-keys "sshpass -p gergej ssh root@192.168.56.101" C-m
sleep 1
tmux send-keys "/opt/karaf/bin/karaf" C-m

tmux select-pane -t 1
tmux send-keys "sshpass -p gergej ssh root@192.168.56.101" C-m
sleep 1
tmux send-keys "tail -f /opt/karaf/data/log/karaf.log" C-m

tmux select-pane -t 2
tmux send-keys "sshpass -p gergej ssh root@192.168.56.102" C-m
sleep 1
tmux send-keys "/opt/karaf/bin/karaf" C-m

tmux select-pane -t 3
tmux send-keys "sshpass -p gergej ssh root@192.168.56.102" C-m
sleep 1
tmux send-keys "tail -f /opt/karaf/data/log/karaf.log" C-m

tmux select-pane -t 4
tmux send-keys "sshpass -p gergej ssh root@192.168.56.103" C-m
sleep 1
tmux send-keys "/opt/karaf/bin/karaf" C-m

tmux select-pane -t 5
tmux send-keys "sshpass -p gergej ssh root@192.168.56.103" C-m
sleep 1
tmux send-keys "tail -f /opt/karaf/data/log/karaf.log" C-m


tmux select-window -t $SESSION:1
tmux -2 attach-session -t $SESSION

