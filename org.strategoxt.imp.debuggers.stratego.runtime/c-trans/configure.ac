AC_PREREQ([2.58])
AC_INIT([libstrdbgruntime],[0.1],[libstrdbgruntime-bugs@libstrdbgruntime.org])
AM_INIT_AUTOMAKE([1.7.2 -Wall -Wno-portability foreign])

# set the prefix immediately to the default prefix
test "x$prefix" = xNONE && prefix=$ac_default_prefix

XT_SETUP
XT_USE_XT_PACKAGES

AC_PROG_CC
AC_PROG_LIBTOOL

### OUTPUT #############################
AC_CONFIG_FILES([
  Makefile
  lib/Makefile
  tools/Makefile
])
AC_OUTPUT
