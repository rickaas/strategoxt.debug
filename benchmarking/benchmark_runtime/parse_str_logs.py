#!/usr/bin/env python

#[ Main | info ] Front-end succeeded
#[ Main | info ] Optimization succeeded -O 2 : [user/system] = [0.07s/0.00s]
#[ Main | info ] Backend preprocessing succeeded : [user/system] = [0.12s/0.00s]
#[ Main | info ] Backend transformation succeeded : [user/system] = [0.16s/0.00s]
#[ Main | info ] Backend optimization succeeded -O 2 : [user/system] = [0.07s/0.00s]
#[ Main | info ] Backend postprocessing succeeded : [user/system] = [0.01s/0.00s]
#[ Main | info ] Pretty printing succeeded   : [user/system] = [0.24s/0.00s]
#[ Main | info ] Compilation succeeded       : [user/system] = [3.22s/0.00s]

PREFIX = "[ Main | info ] "
STEP_POSTFIX = " succeeded"

key_f = "Front-end succeeded"
key_opt = "Optimization succeeded"
key_bpre = "Backend preprocessing succeeded"
key_btr = "Backend transformation succeeded"
key_bopt = "Backend optimization succeeded"
key_bpost = "Backend postprocessing succeeded"
key_pp ="Pretty printing succeeded"
key_com = "Compilation succeeded"
key_steps= [
	key_f, key_opt, key_bpre, key_btr, key_bopt, key_bpost, key_pp, key_com
]

step_duration = { }

for key in key_steps:
	step_duration[key] = []


def resetDuration():
	for key in key_steps:
		step_duration[key] = []

def getKey(line_prefix):
#	print line_prefix
	for key_step in key_steps:
		if line_prefix.startswith(key_step):
			return key_step
	return ""

def canHandleLine(line):
	if line is None or len(line) == 0: 
		return False
	elif line.startswith("Allow debugger to connect"):
		return False
	elif line.startswith("Listening for transport dt_socket at address: 5432"):
		return False
	else:
		return True

def handleLine(line):
	if not canHandleLine(line): 
		return
	parts = line.partition(": [user/system] = ") # triple
	step = parts[0].strip()
	duration = parts[2]
	duration = duration[1:len(duration)-1].partition("s/")[0]
	duration = float(duration)
	step_key = getKey(step[len(PREFIX):])
	#print step_key
	step_duration[step_key].append(duration)

def doStuff(loglocation):
	ins = open(loglocation, "r" )
	array = []
	index = 1
	for line in ins:
		line = line.strip()
		index = index + 1
		#print index
		handleLine(line)
	#print step_duration

def makePlotData(output_location):
	pass
	# step_duration = {}
	#doStuff(loglocation)
	
	f = open(output_location,'w')
	# header
	header = "\t".join([str('"' + k[0:(len(k)-len(STEP_POSTFIX))] + '"') for k in key_steps])
	f.write("iteration\t")
	f.write(header)
	f.write("\n")

	iter_count = len(step_duration[key_steps[0]])
	print iter_count

	for index in range(0, iter_count):
		print index
		f.write(str(index))
		for step_key in key_steps:
			f.write("\t")
			d = step_duration[step_key][index]
			f.write(str(d))
		f.write("\n")
	f.close()

	for step_key in key_steps:
		print step_key
		values = step_duration[step_key]
		print sum(values)/len(values)
projectNames = ["tiny", "prop", "localvar", "dynamicrule", "start" ]
runtypes = [".debug.log", ".debug.javadebug.log", ".release.log", ".release.javadebug.log"]

#loglocation="tiny.release.javadebug.log"
#loglocation="tiny.debug.log"
#loglocation="tiny.debug.javadebug.log"

read_logs = []
for p in projectNames:
	read_logs.append(str(p + ".debug.log"))
	read_logs.append(str(p + ".debug.javadebug.log"))
print read_logs

for read_log in read_logs:
	doStuff(read_log)
makePlotData("debug-stats.log")

resetDuration()

read_logs = []
for p in projectNames:
	read_logs.append(str(p + ".release.log"))
	read_logs.append(str(p + ".release.javadebug.log"))
print read_logs

for read_log in read_logs:
	doStuff(read_log)
makePlotData("release-stats.log")
