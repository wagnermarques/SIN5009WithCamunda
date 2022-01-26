#!/bin/bash
#Comando para startar o camunda
$CAMUNDA_BPM_PLATFORM_HOME/start-camunda.sh &

#use esse comando abaixo pra parar o camunda
#$CAMUNDA_BPM_PLATFORM_HOME/shutdown-camunda.sh

#xterm -hold  -e "tail -f $CAMUNDA_BPM_PLATFORM_HOME/server/apache-tomcat-9.0.19/logs/catalina.out" &
