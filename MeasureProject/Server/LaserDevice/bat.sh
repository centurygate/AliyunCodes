#!/bin/bash
#a=$(pwd)
source /etc/profile
a="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
echo $a
mvn clean
mvn assembly:assembly
clear
echo "-----------start running program---------------"
echo
java -jar $a/target/*with-dependencies.jar
echo
echo "-----------stop  running program---------------"
