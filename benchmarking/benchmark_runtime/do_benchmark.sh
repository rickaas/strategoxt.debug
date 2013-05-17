#!/bin/bash

DIST_DEBUG_LIBDSLDI_DIR="/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/dist/dist-libdsldi/debug"
DIST_RELEASE_LIBDSLDI_DIR="/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/dist/dist-libdsldi/release"

DIST_LIBDSLDI_DIR="$DIST_RELEASE_LIBDSLDI_DIR"

RUN_OPTS=

BENCHMARK_OUTPUT="$GLOBAL_BENCHMARK_OUTPUT"

if [ -z "$GLOBAL_BENCHMARK_OUTPUT" ] ; then
	BENCHMARK_OUTPUT="output.txt"
fi


if [ "USE_DEBUG" == "$1" ]; then
	shift
	DIST_LIBDSLDI_DIR="$DIST_DEBUG_LIBDSLDI_DIR"
fi

if [ "USE_RELEASE" == "$1" ]; then
	shift
	DIST_LIBDSLDI_DIR="$DIST_RELEASE_LIBDSLDI_DIR"
fi

if [ "JAVA_DEBUG" == "$1" ] ; then
	shift
	RUN_OPTS="DEBUG"
	# Allow debugger to connect
fi

if [ "JAVA_DEBUG_WAIT" == "$1" ] ; then
	shift
	RUN_OPTS="DEBUG_SUSPEND"
	# Wait for debugger to attach
fi

echo "args"
echo "$@"
# "DEBUG_SUSPEND"


date >> $BENCHMARK_OUTPUT

START=$(date +%s.%N)



echo $DIST_LIBDSLDI_DIR/run.sh $RUN_OPTS "$@" >> $BENCHMARK_OUTPUT
$DIST_LIBDSLDI_DIR/run.sh $RUN_OPTS "$@"

END=$(date +%s.%N)
DIFF=$(echo "$END - $START" | bc)
echo $DIFF >> $BENCHMARK_OUTPUT

