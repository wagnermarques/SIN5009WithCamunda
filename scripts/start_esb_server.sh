#!/bin/bash
cd $ESB_HOME && ./bin/start
xterm -hold -e "tail -f $ESB_HOME/log/tesb.log" &
