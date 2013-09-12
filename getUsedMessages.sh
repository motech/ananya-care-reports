#!/bin/bash
# use: ./getUsedMessages.sh > messages.properties

dir=./src/main/webapp/resources/lib/*

while read p; do
	if [[ $p == \#* ]] || [[ $p == "" ]]; then
		echo $p
		continue
	fi
	if [[ $(find ./src/ -iregex '.*\(js\|java\|jsp\|html\)' -and -not -path "$dir" | xargs grep -o ${p%=*} * | wc -l) -gt 0 ]]; then
		#echo ${p%=*}
		echo $p
	fi
done < src/main/webapp/messages/messages.properties
