#!/bin/bash
export DEPLOY_TO_IP="127.0.0.1"
export thisRepoDir=/cygdrive/c/Users/wagner/BPMNTasks/SIN5009WithCamunda
export CAMUNDA_BPM_PLATFORM_HOME= /cygdrive/c/Users/wagner/PROGSATIVOS/camunda-bpm-tomcat-7.16.0
export CAMUNDA_BPM_PLATFORM_DEPLOY_DIR=$CAMUNDA_BPM_PLATFORM_HOME/server/apache-tomcat-9.0.19/webapps

export ECLIPSE_HOME=/cygdrive/c/Users/wagner/PROGSATIVOS/eclipse-jee-2021-12-R-win32-x86_64

export M2_HOME=/cygdrive/c/Users/wagner/PROGSATIVOS/apache-maven-3.8.4

export ESB_HOME=/cygdrive/c/Users/wagner/PROGSATIVOS/apache-karaf-4.3.6
export ESB_DEPLOY_DIR=$ESB_HOME/deploy
export START_ESB_CMD="$ESB_HOME/bin/start"

export CAMUNDA_BPM_MODELER_HOME=/cygdrive/c/Users/wagner/PROGSATIVOS/camunda-modeler-4.11.1-win-x64
export SOAPUI_HOME=
export watchLogs="tail -f $CAMUNDA_BPM_PLATFORM_HOME/server/apache-tomcat-9.0.19/logs/catalina.out"

export processes_content_management_directory=/cygdrive/c/Users/wagner/CONTENT_MANAGMENT_DIR
