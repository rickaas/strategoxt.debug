#!/bin/bash
# update

prefix=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`
CPBASE=$prefix/../../dist-libdsldi/

workspacehome=/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi/strategoxt.debug
strdilibdir=$workspacehome/org.strategoxt.imp.debuggers.stratego.jar_tests/lib

rm -rf $CPBASE
mkdir $CPBASE

cp -v `find $strdilibdir -name "*.jar"` $CPBASE

echo `date`
