#!/bin/bash

binbase=/home/rlindeman/Documents/TU/strategoxt/bin-stuff
strjbin=$binbase/strj-from-git-clean

rm -rf $strjbin

#git checkout

cd strategoxt
git clean -fd
git checkout .

cd strategoxt-java-backend


start_date=$(date +"%s")

./bootstrap
./configure --prefix=$strjbin

bc_date=$(date +"%s")

make

make_date=$(date +"%s")

make install

install_date=$(date +"%s")

bc_diff=$(($bc_date-$start_date))
make_diff=$(($make_date-$bc_date))
install_diff=$(($install_date-$make_date))
total_diff=$(($install_date-$start_date))
echo "BC:      $bc_diff"
echo "Make:    $make_diff"
echo "Install: $install_diff"
echo "Total:   $total_diff"
echo "$(($total_diff / 60)) minutes and $(($total_diff % 60)) seconds elapsed."
echo `date`
