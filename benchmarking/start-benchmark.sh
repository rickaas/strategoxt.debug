#!/bin/bash

BENCHMARK_DIR=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`

[ -z "$1" ] && echo "First argument needs to be the stratego home dir" && exit




REPEAT_COUNT=1

STRATEGO_CONTAINER_DIR=`realpath $1`

echo STRATEGO_CONTAINER_DIR=$STRATEGO_CONTAINER_DIR

ANT_STATS_DIR=$STRATEGO_CONTAINER_DIR/stats/ant-stats/
ANT_LOGS_DIR=$STRATEGO_CONTAINER_DIR/stats/ant-logs/

mkdir -p $ANT_STATS_DIR
mkdir -p $ANT_LOGS_DIR

THIRD_PARTY_DIR="$BENCHMARK_DIR/../../thirdparty"

ANT_STAT_EXTRA_ARGS="-Dantstatistics.directory=$ANT_STATS_DIR"
ANT_STAT_ARGS="-lib $THIRD_PARTY_DIR -logger de.pellepelster.ant.statistics.AntStatisticsLogger $ANT_STAT_EXTRA_ARGS"


STR_ARGS= # "-Dstr.instrumentation.enabled=true"

ANT_EXTRA_ARGS="$ANT_STAT_ARGS "

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
	cd $STRATEGO_CONTAINER_DIR/strategoxt/strategoxt
	# this directory contains build.sh
	./build.sh $ANT_EXTRA_ARGS 2>&1 | tee $ANT_LOGS_DIR/java-bootstrap-build_$1.log
}

for i in $(seq $REPEAT_COUNT)
do
   echo "Starting iteration $i"
   CleanAndBuild $i
done
