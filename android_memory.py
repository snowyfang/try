# -*- coding: utf-8 -*-

import os
from threading import Timer
import time
import datetime
import re
import numpy
import pylab

adb_access="/*/platform-tools/adb"


#get native|dalvik|total|etc. data
native_cmd=adb_access+" shell dumpsys meminfo com.package.name | grep 'Native Heap'| head -n 1 | awk '{print $8 }' >> native.txt"



#devices id
devicename = "devices id"  //by adb devices 


#init remove the invalid file
def removeFile(path):
	if os.path.exists(path):
		os.remove(path)
	else:
		return

#a function that save data to file
def native():
	os.system(native_cmd)	


#print device model
def devNamPrint():
	devices_name = adb_access + " devices" 
	output = os.popen(devices_name)
	deviceName = [line.rstrip() for line in output]

	if devicename in deviceName[1]:
		print "devices id"
	elif devicename1 in deviceName[1]:
		print "devices id1"
	else:
		print "Not known devices"

#print data report
def myPrint(path):
	myData = numpy.loadtxt(path)
	m = max(myData)
	s = sum(myData)
	n = len(myData)
	a = float(s/n)
	print path
	print("ava: ", a)
	print ("max: ",m)

#draw line of memery data
def drawLine(path):
	x = list()
	y = list()
	n = 1
	fName = path[:-4]
	with open(path, 'r') as pa:
		for line in pa:
			x.append(n)
			y.append(line)
			n = n + 1
	pylab.plot(x,y)
	pylab.xlabel(u"time")
	pylab.ylabel(fName)
	pylab.savefig(fName + '.png')
	



def run(): 
	removeFile("native.txt")

	d1=datetime.datetime.now()
	d2=d1+datetime.timedelta(seconds=30)
	while datetime.datetime.now() < d2:
		native()
		time.sleep(0.2)
	devNamPrint()
	myPrint("native.txt")
	drawLine("native.txt")
  
run()



