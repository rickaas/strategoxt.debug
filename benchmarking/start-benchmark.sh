#!/bin/bash

BENCHMARK_DIR=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`

echo Arg count "$#"

[ -z "$1" ] && echo "First argument needs to be the stratego home dir" && exit
STRATEGO_CONTAINER_DIR=`realpath $1`

shift

STR_ARGS=

while (( "$#" )); do


if [ "INSTRUMENT" == "$1" ]; then
	# enable stratego instrumentation
	echo Stratego Instrumentation enabled
	STR_ARGS="-Dstr.instrumentation.enabled=true $STR"
fi

if [ "INSTRUMENT_ONLY" == "$1" ]; then
	# only instrument the stratego files
	echo Only perform Stratego Instrumentation
	# Call instrument-all java target
	STR_ARGS="instrument-all -Dstr.instrumentation.enabled=true $STR"
fi

if [ "GENERATE_JAVA" == "$1" ] ; then
	# only generate java
	STR_ARGS="generate-java $str"
fi

if [ "GENERATE_INSTR_JAVA" == "$1" ] ; then
	# instrument, then generate java
	STR_ARGS="generate-java -Dstr.instrumentation.enabled=true $STR"
fi

if [ "COMPILE_JAVA" == "$1" ] ; then
	# Only compiles Java
	STR_ARGS="compile-java $STR"
fi

if [ "ALLOW_DEBUGGER" == "$1" ] ; then
	# Start java tasks with debug parameters
	echo Allow Debugger
fi

shift

done

# number of times we want to repeat the build
# Before each iteration everything is cleaned
REPEAT_COUNT=1


# This directory contains the toplevel git-checkout directory of stratego
echo STRATEGO_CONTAINER_DIR=$STRATEGO_CONTAINER_DIR

ANT_STATS_DIR=$STRATEGO_CONTAINER_DIR/stats/ant-stats/
ANT_LOGS_DIR=$STRATEGO_CONTAINER_DIR/stats/ant-logs/

mkdir -p $ANT_STATS_DIR
mkdir -p $ANT_LOGS_DIR

# arguments to configure AntStatistics
THIRD_PARTY_DIR="$BENCHMARK_DIR/../../thirdparty"
ANT_STAT_EXTRA_ARGS="-Dantstatistics.directory=$ANT_STATS_DIR"
ANT_STAT_ARGS="-lib $THIRD_PARTY_DIR -logger de.pellepelster.ant.statistics.AntStatisticsLogger $ANT_STAT_EXTRA_ARGS"



ANT_EXTRA_ARGS="$ANT_STAT_ARGS $STR_ARGS"

function Prepare {
	echo copy strategoxt.jar
	cp $STRATEGO_CONTAINER_DIR/strategoxt.jar $STRATEGO_CONTAINER_DIR/strategoxt/strategoxt/strategoxt.base.jar
	cp $STRATEGO_CONTAINER_DIR/ecj-3.8.jar $STRATEGO_CONTAINER_DIR/strategoxt/strategoxt/ecj-3.8.jar
}

function GitClean {
	git clean -f -dx .
	git clean -f -X .
	git clean -f -x .
	git checkout .
}

# assume we are in BENCHMARK_DIR and end in the directory with build.sh
function CleanSource {
	cd $STRATEGO_CONTAINER_DIR/strategoxt

	echo clean stratego

	GitClean

	cd strategoxt/syntax/java-front

	echo clean java-front

	GitClean

}


function CleanAndBuild {
	echo CleanAndBuild iteration $1
	echo ==========================================
	echo ==========================================
	echo Clean
	echo ==========================================
	echo ==========================================
	CleanSource

	Prepare
	
	echo ==========================================
	echo ==========================================
	echo Build
	echo ==========================================
	echo ==========================================
	pwd
	cd $STRATEGO_CONTAINER_DIR/strategoxt/strategoxt
	# this directory contains build.sh
	./build.sh $ANT_EXTRA_ARGS 2>&1 | tee $ANT_LOGS_DIR/java-bootstrap-build_$1.log
}

for i in $(seq $REPEAT_COUNT)
do
   echo "Starting iteration $i"
   CleanAndBuild $i
done
