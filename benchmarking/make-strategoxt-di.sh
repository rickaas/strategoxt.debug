#!/bin/bash
#di-strj-from-git

#gitdate1=$(date +"%s")
#rm -rf di-strj
#git clone git://github.com/metaborg/strategoxt.git di-strj
#cd di-strj 
#git checkout
#gitdate2=$(date +"%s")
#gitdiff=$(($gitdate2-$gitdate1))
#echo "$(($gitdiff / 60)) minutes and $(($gitdiff % 60)) seconds elapsed."

INSTR_STR_SRC=strategoxt-di-source

HOME=/home/rlindeman/Documents/TU/strategoxt/git-stuff

INSTR_SOURCE=$HOME/strategoxt
INSTR_TARGET=$HOME/

function gitclean {

echo start git clean

cd strategoxt
git clean -fd
git checkout .
cd ../

cd $INSTR_STR_SRC
git clean -fd
git checkout .
cd ../

echo end git clean

}


function configit {
	
echo start configit

# Now build it!

binbase=/home/rlindeman/Documents/TU/strategoxt/bin-stuff
strjbin=$binbase/strj-di-from-git-clean

rm -rf $strjbin


cd $INSTR_STR_SRC

cd strategoxt-java-backend


start_date=$(date +"%s")

./bootstrap
./configure --prefix=$strjbin

bc_date=$(date +"%s")

cd ../../

echo end configit
}

function copystr {
echo start copystr
	# we are in strategoxt-java-backend
	cp $DSLDI_LIB_HOME/org.strategoxt.imp.debuggers.stratego.runtime.str $INSTR_STR_SRC/strategoxt-java-backend/trans
	cp $DSLDI_LIB_HOME/org.strategoxt.imp.debuggers.stratego.runtime.str $INSTR_STR_SRC/strategoxt-java-backend/java/runtime/org/strategoxt/lang/compat/override
	cp $DSLDI_LIB_HOME/org.strategoxt.imp.debuggers.stratego.runtime.str $INSTR_STR_SRC/strategoxt-java-backend/java/tools/org/strategoxt/tools
echo end copystr
}

function buildit {

echo start buildit

cd $INSTR_STR_SRC

cd strategoxt-java-backend

make

make_date=$(date +"%s")

make install

install_date=$(date +"%s")

bc_diff=$(($bc_date-$start_date))
make_diff=$(($make_date-$bc_date))
install_diff=$(($install_date-$make_date))
total_diff=$(($install_date-$start_date))
echo "INSTR:   $didatediff"
echo "BC:      $bc_diff"
echo "Make:    $make_diff"
echo "Install: $install_diff"
echo "Total:   $total_diff"
echo "$(($total_diff / 60)) minutes and $(($total_diff % 60)) seconds elapsed."
echo `date`

echo end buildit

cd ../../
}

gitclean
#instrument # no need to instrument, we have commit instrumentated files to git because some needed manual correction

#STRATEGO_RUNTIME_STRCFLAGS
#              Stratego compiler flags for STRATEGO_RUNTIME, overriding
#              pkg-config
#  STRATEGO_RUNTIME_XTC
#              XTC Repository for STRATEGO_RUNTIME, overriding pkg-config
#  STRATEGO_RUNTIME_CFLAGS
#              C compiler flags for STRATEGO_RUNTIME, overriding pkg-config
#  STRATEGO_RUNTIME_LIBS
#              linker flags for STRATEGO_RUNTIME, overriding pkg-config
#  STRATEGO_LIB_STRCFLAGS
#              Stratego compiler flags for STRATEGO_LIB, overriding pkg-config
#  STRATEGO_LIB_XTC
#              XTC Repository for STRATEGO_LIB, overriding pkg-config
#  STRATEGO_LIB_CFLAGS
#              C compiler flags for STRATEGO_LIB, overriding pkg-config
#  STRATEGO_LIB_LIBS
#              linker flags for STRATEGO_LIB, overriding pkg-config
#STRATEGOXT_STRCFLAGS
#              Stratego compiler flags for STRATEGOXT, overriding pkg-config

#CDEBUGRUNTIME=strategoxt/org.strategoxt.imp.debuggers.stratego.runtime/c-runtime-include
#DSLDI_LIB_HOME=/home/rlindeman/Documents/TU/strategoxt/git-stuff/dsldi
#export STR_DEBUG_RUNTIME_LIB=$DSLDI_LIB_HOME

# RL: the exported environment are not found in the Makefile, so how does that work?
#export STRATEGOXT_STRCFLAGS="$STRATEGOXT_STRCFLAGS -I $STR_DEBUG_RUNTIME_LIB"
#export STRATEGO_LIB_STRCFLAGS="$STRATEGO_LIB_STRCFLAGS -I $STR_DEBUG_RUNTIME_LIB"
#export STRATEGO_RUNTIME_STRCFLAGS="$STRATEGO_RUNTIME_STRCFLAGS -I $STR_DEBUG_RUNTIME_LIB"

configit # run ./bootstrap and configure
#copystr
buildit # call make && make install
