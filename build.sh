#!/bin/bash

STRATEGOXT_DEBUG_DIR=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`

DIST_DIR_BASE=$STRATEGOXT_DEBUG_DIR/../dist

ECJ_DIR=$DIST_DIR_BASE/ecj

function GitClean {
	git clean -f -dx .
	git clean -f -X .
	git clean -f -x .
	git checkout .
}

function DistCopy {
	echo Dist $DIST_CONFIG
	# copy runtime, no difference between Release and Debug
	rm -rf $DIST_DIR_BASE/str-runtime
	mkdir -p $DIST_DIR_BASE/str-runtime
	cp $STR_RUNTIME_HOME/jar/* $DIST_DIR_BASE/str-runtime
	cp $STR_RUNTIME_HOME/str-lib/* $DIST_DIR_BASE/str-runtime
	
	# copy instrumentation
	rm -rf $DIST_DIR_BASE/str-instrument/$DIST_CONFIG
	mkdir -p $DIST_DIR_BASE/str-instrument/$DIST_CONFIG
	cp $STR_INSTRUMENT_HOME/build/jar/* $DIST_DIR
}

DIST_CONFIG=
if [ "DIST_DEBUG" == "$1" ]; then
	echo Distribute Debug
	DIST_CONFIG="debug"
	shift
fi
if [ "DIST_RELEASE" == "$1" ]; then
	echo Distribute Release
	DIST_CONFIG="release"
	shift
fi

# clean all the stuff
GitClean

STR_RUNTIME_HOME=$STRATEGOXT_DEBUG_DIR/org.strategoxt.imp.debuggers.stratego.runtime
# build org.strategoxt.imp.debuggers.stratego.runtime
cd $STR_RUNTIME_HOME
ant -f build.main.xml -lib $ECJ_DIR

# build stratego-di
STR_INSTRUMENT_HOME=$STRATEGOXT_DEBUG_DIR/org.strategoxt.imp.debuggers.stratego.instrumentation
cd $STR_INSTRUMENT_HOME
ARGS="-lib $DIST_DIR_BASE/dist-libdsldi/release"
ANT_OPTS="-Xss8m -Xmx1024m -server -XX:+UseParallelGC -XX:MaxPermSize=256m $EXTRA_ANT_OPTS" ant -f build.main.xml -lib $ECJ_DIR $ARGS "$@"

if [ -n "$DIST_CONFIG" ]; then
	# dist is configured, copy it
	DistCopy
fi