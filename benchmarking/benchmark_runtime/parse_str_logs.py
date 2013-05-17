#!/usr/bin/env python

import os.path

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


def resetDuration():
	step_duration = {}

def getKey(line_prefix):
#	print line_prefix
	for key_step in key_steps:
		if line_prefix.startswith(key_step):
			return key_step
	return ""

def addDuration(program_name, step_key, duration):
	key = (program_name, step_key)
	if not key in step_duration:
		step_duration[key] = []
	step_duration[key].append(duration)
	
def canHandleLine(line):
	if line is None or len(line) == 0: 
		return False
	elif line.startswith("Allow debugger to connect"):
		return False
	elif line.startswith("Listening for transport dt_socket at address: 5432"):
		return False
	else:
		return True

def handleLine(program_name, line):
	if not canHandleLine(line): 
		return
	#print line
	parts = line.partition(": [user/system] = ") # triple
	step = parts[0].strip()
	duration = parts[2]
	duration = duration[1:len(duration)-1].partition("s/")[0]
	duration = float(duration)
	step_key = getKey(step[len(PREFIX):])
	#print step_key
	addDuration(program_name, step_key, duration)

def doStuff(name, loglocation):
	ins = []
	try:
		ins = open(loglocation, "r" )
	except IOError, e:
		print e
		return
	array = []
	index = 1
	for line in ins:
		line = line.strip()
		index = index + 1
		handleLine(name, line)
	#print step_duration

def makePlotDataRow(program_name, program_iteration, output_file):
	output_file.write(str(program_iteration))
	output_file.write("\t")
	output_file.write(str(program_name))

	for step_key in key_steps:
		key = (program_name, step_key)
		d = step_duration[key][program_iteration]
		output_file.write("\t")
		output_file.write(str(d))
	output_file.write("\n")

def makePlotData(output_location, program_names):
	pass
	# step_duration = {}
	#doStuff(loglocation)
	
	f = open(output_location,'w')
	# header
	header = "\t".join([str('"' + k[0:(len(k)-len(STEP_POSTFIX))] + '"') for k in key_steps])
	f.write("iteration\t")
	f.write("program_name\t")
	f.write(header)
	f.write("\n")
	# key of step_duration is (program_name, step_key)


	# validate that each program has the same number
	for program_name in program_names:
		program_iterations = len(step_duration.itervalues().next())
		for program_iteration in range(0, program_iterations):
			makePlotDataRow(program_name, program_iteration, f)
	f.close()
	return
#			ordered_keys.append(step_key)
#		keys_per_program[program_name] = ordered_keys
	
	for index, val in enumerate(ordered_keys):
		print index
		f.write(str(index))
		f.write("\t")
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
#projectNames = ["tiny"]
runtypes = [".debug.log", ".debug.javadebug.log", ".release.log", ".release.javadebug.log"]

#loglocation="tiny.release.javadebug.log"
#loglocation="tiny.debug.log"
#loglocation="tiny.debug.javadebug.log"

#basedir="output/long_tests/"
#basedir="output/detailed_tests_silent/"
#basedir="output/detailed_tests/"
basedir="output/detailed_nocurrenttostring/"

read_logs = []
for p in projectNames:
	read_logs.append(str(p + ".debug.log"))
	read_logs.append(str(p + ".debug.javadebug.log"))
read_logs = filter(lambda filename: os.path.isfile(basedir+filename), read_logs)
print read_logs

for read_log in read_logs:
	doStuff(read_log, basedir + read_log)
makePlotData(basedir + "debug-stats.log", read_logs)

resetDuration()

read_logs = []
for p in projectNames:
	read_logs.append(str(p + ".release.log"))
	read_logs.append(str(p + ".release.javadebug.log"))
read_logs = filter(lambda filename: os.path.isfile(basedir+filename), read_logs)
print read_logs

for read_log in read_logs:
	doStuff(read_log, basedir + read_log)
makePlotData(basedir + "release-stats.log", read_logs)
