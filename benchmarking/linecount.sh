#!/bin/bash

# linecount.sh FILE COUNT_TYPE
# FILE location of a file
# COUNT_TYPE = usewc | usepp
#              usewc: count all lines (including blank and comments)
#              usepp: pretty-print the file (comments are dropped, lines can be split/joined together) then count all non-blank lines

#INSTR_STR_SRC=strategoxt-di-source

#cd $INSTR_STR_SRC

#files=$(find . -type f -name "*.str")
#echo $1

prefix=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`

file=$1
#echo $file
count_type=$2

echoerr() { echo "$@" 1>&2; }


if [ "$count_type" == "usewc" ]
then
wc -l "$file" | cut -f1 -d' '
fi
if [ "$count_type" == "usepp" ]
then
set -o pipefail
#parse-stratego -i $file | pp-aterm | ast2text -p $prefix/Stratego-pretty.pp | sed '/^\s*$/d' | wc -l
echoerr $file
pp-stratego -i $file | sed '/^\s*$/d' | wc -l
fi
