include $(top_srcdir)/Makefile.xt
include $(wildcard *.dep)

lib_LTLIBRARIES = libstrdbgruntime.la 
pkgdata_DATA    = libstrdbgruntime.rtree
EXTRA_DIST      = $(ostrdbgruntimesrc) libstrdbgruntime.rtree
CLEANFILES      = libstrdbgruntime.c libstrdbgruntime.rtree

libstrdbgruntime_la_SOURCES  = libstrdbgruntime.c
libstrdbgruntime_la_LDFLAGS  = -avoid-version -no-undefined
libstrdbgruntime_la_CPPFLAGS = \
  $(STRATEGO_LIB_CFLAGS) $(STRATEGO_RUNTIME_CFLAGS) $(ATERM_CFLAGS)
libstrdbgruntime_la_LIBADD   = \
  $(STRATEGO_LIB_LIBS) $(STRATEGO_RUNTIME_LIBS) $(ATERM_LIBS)
#  $(STRATEGO_SGLR_LIBS) $(STRATEGO_LIB_LIBS) $(STRATEGO_RUNTIME_LIBS) $(ATERM_LIBS)

strdbgruntimesrc = \
  strdbgruntime.str \
  $(wildcard $(srcdir)/strdbgruntime/*.str) \
  $(wildcard $(srcdir)/strdbgruntime/stratego/*.str)

#STRINCLUDES = -I $(top_srcdir)/syn

libstrdbgruntime.c libstrdbgruntime.rtree : $(strdbgruntimesrc)
	@$(STRC)/bin/strc -c --library -i strdbgruntime.str -o libstrdbgruntime.rtree
	rm libstrdbgruntime.str
#@$(STRC)/bin/strc -c --library -i strdbgruntime.str -o libstrdbgruntime.rtree # $(STRINCLUDES)
