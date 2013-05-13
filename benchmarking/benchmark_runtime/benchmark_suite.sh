#!/bin/bash
prefix=`dirname "$(cd ${0%/*}/ && echo $PWD/${0##*/})"`

# benchmark suite
MINI_PROGRAMS=../../examples-projects/mini-programs
REPEAT_COUNT=1

export GLOBAL_BENCHMARK_OUTPUT="global_bm.txt"

function DoBenchmarkUseDebug {
	# $1 = JAVACLASS
	# $2 = INPUT
	# $3 = OUTPUT
	# $4 = BENCHMARKARGS
	for i in $(seq $REPEAT_COUNT)
	do
		echo "Starting iteration $i"
		rm -rf $3
		mkdir -p $3
		./do_benchmark.sh USE_DEBUG DO_REDIRECT $1.debug.log -i $2 -o $3/$1.java
	done
}

function DoBenchmarkUseDebugJavaDebug {
	# $1 = JAVACLASS
	# $2 = INPUT
	# $3 = OUTPUT
	# $4 = BENCHMARKARGS
	for i in $(seq $REPEAT_COUNT)
	do
		echo "Starting iteration $i"
		rm -rf $3
		mkdir -p $3
		./do_benchmark.sh USE_DEBUG JAVA_DEBUG DO_REDIRECT $1.debug.javadebug.log -i $2 -o $3/$1.java
	done
}

function DoBenchmarkUseRelease {
	# $1 = JAVACLASS
	# $2 = INPUT
	# $3 = OUTPUT
	# $4 = BENCHMARKARGS
	for i in $(seq $REPEAT_COUNT)
	do
		echo "Starting iteration $i"
		rm -rf $3
		mkdir -p $3
		./do_benchmark.sh USE_RELEASE DO_REDIRECT $1.release.log -i $2 -o $3/$1.java
	done
}

function DoBenchmarkUseReleaseJavaDebug {
	# $1 = JAVACLASS
	# $2 = INPUT
	# $3 = OUTPUT
	# $4 = BENCHMARKARGS
	for i in $(seq $REPEAT_COUNT)
	do
		echo "Starting iteration $i"
		rm -rf $3
		mkdir -p $3
		./do_benchmark.sh USE_RELEASE JAVA_DEBUG DO_REDIRECT $1.release.javadebug.log -i $2 -o $3/$1.java
	done
}

echo =====================
echo =====================
echo 001_tiny

rm tiny.debug.log
DoBenchmarkUseDebug tiny $MINI_PROGRAMS/001_tiny/tiny.str $prefix/output/001_tiny/debug

rm tiny.debug.javadebug.log
DoBenchmarkUseDebugJavaDebug tiny $MINI_PROGRAMS/001_tiny/tiny.str $prefix/output/001_tiny/debug

echo ------------

rm tiny.release.log
DoBenchmarkUseRelease tiny $MINI_PROGRAMS/001_tiny/tiny.str $prefix/output/001_tiny/release

rm tiny.release.javadebug.log
DoBenchmarkUseReleaseJavaDebug tiny $MINI_PROGRAMS/001_tiny/tiny.str $prefix/output/001_tiny/release

echo =====================
echo =====================
echo 002_test-prop

rm prop.debug.log
DoBenchmarkUseDebug prop $MINI_PROGRAMS/002_test-prop/test-prop.str $prefix/output/002_test-prop/debug

rm prop.debug.javadebug.log
DoBenchmarkUseDebugJavaDebug prop $MINI_PROGRAMS/002_test-prop/test-prop.str $prefix/output/002_test-prop/debug

echo ------------

rm prop.release.log
DoBenchmarkUseRelease prop $MINI_PROGRAMS/002_test-prop/test-prop.str $prefix/output/002_test-prop/release

rm prop.release.javadebug.log
DoBenchmarkUseReleaseJavaDebug prop $MINI_PROGRAMS/002_test-prop/test-prop.str $prefix/output/002_test-prop/release


echo =====================
echo =====================
echo 003_localvar

rm localvar.debug.log
DoBenchmarkUseDebug localvar $MINI_PROGRAMS/003_localvar/localvar.str $prefix/output/003_localvar/debug

rm localvar.debug.javadebug.log
DoBenchmarkUseDebugJavaDebug localvar $MINI_PROGRAMS/003_localvar/localvar.str $prefix/output/003_localvar/debug

echo ------------

rm localvar.release.log
DoBenchmarkUseRelease localvar $MINI_PROGRAMS/003_localvar/localvar.str $prefix/output/003_localvar/release

rm localvar.release.javadebug.log
DoBenchmarkUseReleaseJavaDebug localvar $MINI_PROGRAMS/003_localvar/localvar.str $prefix/output/003_localvar/release


echo =====================
echo =====================
echo 004_dynamicrule

rm dynamicrule.debug.log
DoBenchmarkUseDebug dynamicrule $MINI_PROGRAMS/004_dynamicrule/localvar.str $prefix/output/004_dynamicrule/debug

rm dynamicrule.debug.javadebug.log
DoBenchmarkUseDebugJavaDebug dynamicrule $MINI_PROGRAMS/004_dynamicrule/localvar.str $prefix/output/004_dynamicrule/debug

echo ------------

rm dynamicrule.release.log
DoBenchmarkUseRelease dynamicrule $MINI_PROGRAMS/004_dynamicrule/localvar.str $prefix/output/004_dynamicrule/release

rm dynamicrule.release.javadebug.log
DoBenchmarkUseReleaseJavaDebug dynamicrule $MINI_PROGRAMS/004_dynamicrule/localvar.str $prefix/output/004_dynamicrule/release


echo =====================
echo =====================
echo 005_multiplefiles

rm start.debug.log
DoBenchmarkUseDebug start $MINI_PROGRAMS/005_multiplefiles/start.str $prefix/output/005_multiplefiles/debug

rm start.debug.javadebug.log
DoBenchmarkUseDebugJavaDebug start $MINI_PROGRAMS/005_multiplefiles/start.str $prefix/output/005_multiplefiles/debug

echo ------------

rm start.release.log
DoBenchmarkUseRelease start $MINI_PROGRAMS/005_multiplefiles/start.str $prefix/output/005_multiplefiles/release

rm start.release.javadebug.log
DoBenchmarkUseReleaseJavaDebug start $MINI_PROGRAMS/005_multiplefiles/start.str $prefix/output/005_multiplefiles/release

