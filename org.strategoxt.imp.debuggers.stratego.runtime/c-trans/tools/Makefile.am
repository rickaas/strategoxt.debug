include $(top_srcdir)/Makefile.xt
include $(wildcard *.dep)

bin_PROGRAMS = foobar
foobar_LDADD = \ 
  $(top_builddir)/lib/org.strategoxt.imp.debuggers.stratego.runtime.la \
  $(STRATEGO_LIB_LIBS) $(STRATEGO_RUNTIME_LIBS) $(ATERM_LIBS)

STRINCLUDES    = -I $(top_srcdir)/lib
# RL: we do not have syn
#STRINCLUDES    = -I $(top_srcdir)/lib -I $(top_srcdir)/syn
# we have the default strategy called main
#STRCFLAGS      = --main io-$*

EXTRA_DIST = $(wildcard *.str) $(wildcard *.meta)
CLEANFILES = $(wildcard *.c) $(wildcard *.dep)
