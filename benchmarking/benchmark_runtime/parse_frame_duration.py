#!/usr/bin/env python

# FrameDuration.StrMainProfilerWrapper.log.trace
#"strategoxt/stratego-libraries/lib/spec/collection/hash-table/common.str"	"92,2,93,55"	"hashtable-get"	1368731776425	1368731776426
#"strategoxt/stratego-libraries/lib/spec/collection/hash-table/common.str"	"92,2,93,55"	"hashtable-get"	1368731776437	1368731776437
#"strategoxt/stratego-libraries/lib/spec/collection/hash-table/common.str"	"92,2,93,55"	"hashtable-get"	1368731776456	1368731776457
#"strategoxt/stratego-libraries/lib/spec/collection/hash-table/common.str"	"92,2,93,55"	"hashtable-get"	1368731776468	1368731776468

loglocation = "FrameDuration.StrMainProfilerWrapper.log.trace"

def loopData():
	linenumber = 1
	total_duration = 0
	duration_error_counter = 0
	ins = open(loglocation, "r" )
	for line in ins:
		parts = line.split("\t")
		print len(parts)
		start = long(parts[3])
		end = long(parts[4])
		duration = end-start
		if (duration > 0 and duration < 1000000):
			print str(linenumber) +" : " +str(duration)
			total_duration  = total_duration + duration
			print 
		if (duration > 999999):
			duration_error_counter = duration_error_counter + 1
		pass
		linenumber = linenumber + 1
	print "done"
	print total_duration
	print duration_error_counter
	
loopData()
