# !/bin/sh

# su postgres
companyName='8Seconds'

DIR=/home/root/csl
JAVA_HOME=`echo $JAVA_HOME`

# CHECK WATCHDOG
commandCount=`ps -ef | grep -v "grep" | grep "watchdogs" | wc -l`

if [ "$commandCount" -ge "1" ]; then 
	kill -9 `pgrep -f watchdogs`
fi

# CHECK DIRECTORY
if [ $DIR ]; then
  	echo "Exist DIR"
else
	mkdir /home/root/csl
fi

wget -P /home/root/csl http://ipecho.net/plain

ipAddr=`cat /home/root/csl/plain`

# execute jar

/esl/common/ejre1.8/bin/java -jar /home/root/csl/SendStoreInfo.jar ${ipAddr} ${companyName}

rm /home/root/csl/plain

# echo $ipAddr