#!/usr/bin/env python3.6
# -*- coding: utf-8 -*-
import os,sys,time
#with open('test.py', 'r') as f:
 #   print(f.read())
print("sys path = %s"% sys.path[0])
timestr = time.strftime('%Y-%m-%d %H:%M:%S',time.localtime(time.time()))
print("this is {0} format output,time= {1} ,name = {2}".format("first",timestr,"ljy"))