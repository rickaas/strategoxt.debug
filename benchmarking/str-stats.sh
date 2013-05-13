#!/bin/bash

# str-stats.sh BASEDIR
# BASEDIR points to a directory with stratego files
# The program will:
# * count all .str files
# * Count all lines (including blank and comments) in stratego files
# * Line count (no blanks, no comments and pretty printed)

BASEDIR=$1
if [ -z "${BASEDIR}" ]; then
    echo "BASEDIR is unset or set to the empty string"
fi
#prefix=`dirname "$(cd ${0%/*}/.. && echo $PWD/${0##*/})"`
prefix=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`
#echo $prefix

echo doing metrics for $BASEDIR

echo metrics program located in $prefix

METRICS_FILE=stats.txt

function strategometrics {
	rm $METRICS_FILE
	echo Metrics > $METRICS_FILE
	
	# count all .str files
	echo "Count all .str files" >> $METRICS_FILE
	find $BASEDIR -type f -name "*.str" | wc -l >> $METRICS_FILE

	#Count all lines (including blank and comments) in stratego files
	echo "Count all lines (including blank and comments) in stratego files" >> $METRICS_FILE
	rm lines.txt
	find $BASEDIR -type f -name "*.str" -exec $prefix/linecount.sh {} usewc \; > lines.txt
	paste -sd+ lines.txt | bc >> $METRICS_FILE

	#real lines (no blanks, no comments and pretty printed)
	echo "Line count (no blanks, no comments and pretty printed)"  >> $METRICS_FILE
	rm lines.txt
	find $BASEDIR -type f -name "*.str" -exec $prefix/linecount.sh {} usepp \; > lines.txt
	paste -sd+ lines.txt | bc >> $METRICS_FILE
	
	echo "file count" >> $METRICS_FILE
	wc -l lines.txt >> $METRICS_FILE

	#Stratego file size
#	find $BASEDIR -name '*.str' -exec ls -l {} \; | awk '{ Total += $5} END { print Total }'
}

strategometrics
