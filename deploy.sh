#!/usr/bin/env bash

REPOSITORY=/home/ec2-user/deploy/velog_backend
cd $REPOSITORY

APP_NAME=action_codedeploy
#JAR_NAME=$(ls $REPOSITORY/build/libs/ | grep '.jar' | tail -n 1)
JAR_PATH=$REPOSITORY/build/libs/velog_backend-0.0.1-SNAPSHOT.jar

#CURRENT_PID=$(pgrep -f $APP_NAME)
CURRENT_PID=$(pgrep -f java)
echo CURRENT_PID

if [ -z $CURRENT_PID ]
then
  echo "> 종료할것 없음."
else
  echo "> kill -9 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 5
fi

echo "> $JAR_PATH 배포"
nohup java -jar $JAR_PATH --spring.profiles.active=dev > /dev/null 2> /dev/null < /dev/null &