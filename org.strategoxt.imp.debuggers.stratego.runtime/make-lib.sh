#!/bin/sh

SRC=trans
PACK_NAME=org.strategoxt.imp.debuggers.stratego.runtime
PACK_FOLDER=org/strategoxt/imp/debuggers/stratego/runtime

MAIN_STR=stratego/strategodebugruntime.str
NAME=strategodebugruntime

# create an rtree in str-lib
strj -i ${SRC}/${MAIN_STR} -o build/str-lib/${PACK_NAME}.ctree -I ${SRC} --clean --library -p ${PACK_NAME} -F

# compile to java in java-str-lib
mkdir -p build/java-str-lib/${PACK_FOLDER}
strj -i ${SRC}/${MAIN_STR} -o build/java-str-lib/${PACK_FOLDER}/strategies/${NAME}.java -I ${SRC} --clean --library -p ${PACK_NAME}.strategies -la ${PACK_NAME} 
