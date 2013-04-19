#!/bin/bash
prefix=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`
echo $prefix

rm -rf src
mkdir -p src/sub

for f in {00..99}
do
	(echo module script$f && echo imports libstrategolib && echo strategies && echo foo$f = debug\(!\"foo$f \"\) )  > "src/sub/script$f.str"
done

MAIN_SCRIPT=src/mainpoint.str

echo module mainpoint >> $MAIN_SCRIPT
echo imports sub/- >> $MAIN_SCRIPT
echo strategies >> $MAIN_SCRIPT
echo main = debug\(!\"MAIN: \"\) >> $MAIN_SCRIPT

for f in {00..99}
do
	echo \;foo$f >> $MAIN_SCRIPT
done

rm -rf java-gen
mkdir java-gen

CPBASE="$prefix/../../../dist-libdsldi/"
JAVA_OPTS="-Xss8m -server"

CP="$CPBASE/strategoxt.jar"

java -cp $CP $JAVA_OPTS org.strategoxt.strj.Main -i src/mainpoint.str -o java-gen/big/Main.java -p big

rm -rf bin
mkdir bin
ecj -source 1.5 -cp "java-gen:$CP" java-gen

java -cp "java-gen:$CP" big/Main
