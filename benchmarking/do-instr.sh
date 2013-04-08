#!/bin/bash

BASEDIR=$1
if [ -z "${BASEDIR}" ]; then
    echo "BASEDIR is unset or set to the empty string"
	BASEDIR="."
fi
INSTR_FILE=$2


prefix=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`

INSTR_SOURCE=$BASEDIR
INSTR_TARGET="$prefix/../../temp"

CPBASE=$prefix/../../dist-libdsldi/

mainclass=org.strategoxt.imp.debuggers.stratego.instrumentation.Main

cp="$CPBASE/stratego-di.jar:$CPBASE/*"
#javaopts="-Xss8m -agentlib:hprof=heap=sites"
javaopts="-Xss8m"

failed="--report-failed-files $BASEDIR/failed-files.log"
succeeded="--report-succeeded-files $BASEDIR/succeeded-files.log"
runtimeprops="" #"--report-runtime-properties $BASEDIR/runtimeprops.log"

include_opts="-I ../../java-front/syntax"
di_opts="--verbose 1 --statistics 1 $failed $succeeded $runtimeprops $include_opts"

di_input_file="--input-file ${INSTR_FILE}"
if [ -z "${INSTR_FILE}" ]; then
	di_input_file=""
fi


di_input=$INSTR_SOURCE
# overwrite the existing strategoxt sources
# If we cannot instrument a file we will just use the original file
di_output=$INSTR_TARGET


didate1=$(date +"%s")
java $javaopts -cp $cp $mainclass --input-dir $di_input --output-dir $di_output $di_input_file $di_opts


didate2=$(date +"%s")
didatediff=$(($didate2-$didate1))
echo "$(($didatediff / 60)) minutes and $(($didatediff % 60)) seconds elapsed."

