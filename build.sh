#!/bin/bash

STRATEGOXT_DEBUG_DIR=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`

DIST_DIR_BASE=$STRATEGOXT_DEBUG_DIR/../dist

ECJ_DIR=$DIST_DIR_BASE/ecj

STR_RUNTIME_HOME=$STRATEGOXT_DEBUG_DIR/org.strategoxt.imp.debuggers.stratego.runtime
STR_INSTRUMENT_HOME=$STRATEGOXT_DEBUG_DIR/org.strategoxt.imp.debuggers.stratego.instrumentation

function GitClean {
	git clean -f -dx .
	git clean -f -X .
	git clean -f -x .
	git checkout .
}

function Prepare {
	# copy stuff to utils
	mkdir $STR_INSTRUMENT_HOME/utils
	cp $STRATEGOXT_DEBUG_DIR/../spoofax-project-utils/* $STR_INSTRUMENT_HOME/utils
	# copy all dist-libdsldi/release stuff
	cp $DIST_DIR_BASE/dist-libdsldi/release/* $STR_INSTRUMENT_HOME/utils
	cp $DIST_DIR_BASE/dist-libdsldi/release/libdsldi.rtree $STR_INSTRUMENT_HOME/lib
	cp $DIST_DIR_BASE/dist-libdsldi/release/org.strategoxt.imp.debuggers.stratego.runtime.rtree $STR_INSTRUMENT_HOME/lib
}

function DistCopy {
	echo Dist $DIST_CONFIG
	# copy runtime, no difference between Release and Debug
	rm -rf $DIST_DIR_BASE/str-runtime
	mkdir -p $DIST_DIR_BASE/str-runtime
	cp $STR_RUNTIME_HOME/build/jar/* $DIST_DIR_BASE/str-runtime
	cp $STR_RUNTIME_HOME/build/str-lib/* $DIST_DIR_BASE/str-runtime
	
	# copy instrumentation
	rm -rf $DIST_DIR_BASE/str-instrument/$DIST_CONFIG
	mkdir -p $DIST_DIR_BASE/str-instrument/$DIST_CONFIG
	cp $STR_INSTRUMENT_HOME/build/jar/* $DIST_DIR
}

DIST_CONFIG=
if [ "DIST_DEBUG" == "$1" ]; then
	echo Distribute Debug
	DIST_CONFIG="debug"
	ARGS="$ARGS -Dstr.instrumentation.enabled=true"
#	ARGS="$ARGS -lib $DIST_DIR_BASE/dist-libdsldi/release"
	
	shift
fi
if [ "DIST_RELEASE" == "$1" ]; then
	echo Distribute Release
	DIST_CONFIG="release"
	shift
fi

# clean all the stuff
GitClean
# Copy some stuff
Prepare


# build org.strategoxt.imp.debuggers.stratego.runtime
cd $STR_RUNTIME_HOME
ant -f build.main.xml -lib $ECJ_DIR "-Dlib.dir=$DIST_DIR_BASE/dist-libdsldi/release"

exit

# build stratego-di
cd $STR_INSTRUMENT_HOME

ANT_OPTS="-Xss8m -Xmx1024m -server -XX:+UseParallelGC -XX:MaxPermSize=256m $EXTRA_ANT_OPTS" ant -f build.main.xml -lib $ECJ_DIR $ARGS "$@"

if [ -n "$DIST_CONFIG" ]; then
	# dist is configured, copy it
	DistCopy
fi
