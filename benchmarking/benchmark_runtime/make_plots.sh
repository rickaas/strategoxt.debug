#!/bin/bash

function MakePlot {
gnuplot -persist << EOF
#set size 2;
#set terminal wxt size 600,600

set term png enhanced size 800,600
set output "$OUTPUT_PNG"
#set output outputpng
#set size 2;

#set term epslatex
#set output "graph.eps"

# Where to put the legend
# and what it should contain
#set key invert reverse Left outside
set key Left outside
set key autotitle columnheader

set title "StrategoXT compile steps duration"

set ylabel "duration in seconds"
set xlabel "iteration"
set grid y

#unset xtics

# Define plot style 'stacked histogram'
# with additional settings
set style data histogram
set style histogram rowstacked
set style fill solid 0.95 border -1 # color
#set style fill pattern 4 border
set boxwidth 0.5 relative

#set size ratio 1.5



# set the style for the set 1, 2, 3...
set style line 1 linecolor rgbcolor "red" linewidth 1
set style line 2 linecolor rgbcolor "green" linewidth 1
set style line 3 linecolor rgbcolor "blue" linewidth 1
set style line 4 linecolor rgbcolor "purple" linewidth 1
set style line 5 linecolor rgbcolor "cyan" linewidth 1
set style line 6 linecolor rgbcolor "yellow" linewidth 1
set style line 7 linecolor rgbcolor "pink" linewidth 1
set style line 8 linecolor rgbcolor "orange" linewidth 1

set style increment user

set xtics nomirror rotate by -45 font ",8"

# compile.dat
#plot filename using 2:xtic(1) \
#plot "output/long_tests/debug-stats.log" using 1;
plot "$DATA" using 3:xtic(stringcolumn(1).stringcolumn(2)) \
	, "" using 4 \
	, "" using 5 \
	, "" using 6 \
	, "" using 7 \
	, "" using 8 \
	, "" using 9 \
	, "" using 10 with lines \
#	, "" using 0:2:2 with labels offset 5, 0 notitle \
#	, "" using 0:($2+$3):3 with labels offset 5, 0 notitle \
#	, "" using 0:($2+$3+$4):4 with labels offset 5, 0 notitle \
#	, "" using 0:($2+$3+$4+$5):5 with labels offset 5, 0 notitle \
	;
EOF
}

basedir="output/long_tests"
DATA="$basedir/debug-stats.log"
OUTPUT_PNG="$basedir/debug_compile_duration.png"
MakePlot
DATA="$basedir/release-stats.log"
OUTPUT_PNG="$basedir/release_compile_duration.png"
MakePlot


basedir="output/detailed_tests_silent"
DATA="$basedir/debug-stats.log"
OUTPUT_PNG="$basedir/debug_compile_duration.png"
MakePlot
DATA="$basedir/release-stats.log"
OUTPUT_PNG="$basedir/release_compile_duration.png"
MakePlot



basedir="output/detailed_tests"
DATA="$basedir/debug-stats.log"
OUTPUT_PNG="$basedir/debug_compile_duration.png"
MakePlot
DATA="$basedir/release-stats.log"
OUTPUT_PNG="$basedir/release_compile_duration.png"
MakePlot


basedir="output/detailed_nocurrenttostring"
DATA="$basedir/debug-stats.log"
OUTPUT_PNG="$basedir/debug_compile_duration.png"
MakePlot
DATA="$basedir/release-stats.log"
OUTPUT_PNG="$basedir/release_compile_duration.png"
MakePlot

basedir="output/ant_build"
DATA="$basedir/debug-stats.log"
OUTPUT_PNG="$basedir/debug_compile_duration.png"
MakePlot

basedir="output/ant_build"
DATA="$basedir/release-stats.log"
OUTPUT_PNG="$basedir/release_compile_duration.png"
MakePlot
