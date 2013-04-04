include $(top_srcdir)/Makefile.xt
include $(wildcard *.dep)

lib_LTLIBRARIES = org.strategoxt.imp.debuggers.stratego.runtime.la 
pkgdata_DATA    = org.strategoxt.imp.debuggers.stratego.runtime.rtree
EXTRA_DIST      = $(oorg.strategoxt.imp.debuggers.stratego.runtimesrc) org.strategoxt.imp.debuggers.stratego.runtime.rtree
CLEANFILES      = org.strategoxt.imp.debuggers.stratego.runtime.c org.strategoxt.imp.debuggers.stratego.runtime.rtree

org.strategoxt.imp.debuggers.stratego.runtime_la_SOURCES  = org.strategoxt.imp.debuggers.stratego.runtime.c
org.strategoxt.imp.debuggers.stratego.runtime_la_LDFLAGS  = -avoid-version -no-undefined
org.strategoxt.imp.debuggers.stratego.runtime_la_CPPFLAGS = \
  $(STRATEGO_LIB_CFLAGS) $(STRATEGO_RUNTIME_CFLAGS) $(ATERM_CFLAGS)
org.strategoxt.imp.debuggers.stratego.runtime_la_LIBADD   = \
  $(STRATEGO_LIB_LIBS) $(STRATEGO_RUNTIME_LIBS) $(ATERM_LIBS)
#  $(STRATEGO_SGLR_LIBS) $(STRATEGO_LIB_LIBS) $(STRATEGO_RUNTIME_LIBS) $(ATERM_LIBS)

org.strategoxt.imp.debuggers.stratego.runtimesrc = \
  org.strategoxt.imp.debuggers.stratego.runtime.str \
  $(wildcard $(srcdir)/stratego/*.str)

# RL: not needed
#STRINCLUDES = -I $(top_srcdir)/syn

org.strategoxt.imp.debuggers.stratego.runtime.c org.strategoxt.imp.debuggers.stratego.runtime.rtree : $(org.strategoxt.imp.debuggers.stratego.runtimesrc)
	@$(STRC)/bin/strc -c --library -i org.strategoxt.imp.debuggers.stratego.runtime.str -o org.strategoxt.imp.debuggers.stratego.runtime.rtree
	rm org.strategoxt.imp.debuggers.stratego.runtime.str
#@$(STRC)/bin/strc -c --library -i strdbgruntime.str -o libstrdbgruntime.rtree # $(STRINCLUDES)
