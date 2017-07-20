#!/usr/bin/env python 
# -*- coding: utf-8 -*-
import os,os.path,datetime,time,sys

def diffDays(time1,time2):
	if num>= 0 :
		return "true"
	else:
	    return "false"

rootdir = "C:/Users/liangjy/Desktop"                              
rootdir = unicode(rootdir, "utf8")
createTip=unicode(" 创建时间: ", "utf8")
modifyTip=unicode(" 修改时间: ", "utf8")
accessTip=unicode(" 访问时间: ", "utf8")

daysNum = int(sys.argv[1])
currenttime=datetime.datetime.now()
for parent,dirnames,filenames in os.walk(rootdir):  
	for filename in filenames:   
	    path= os.path.join(parent,filename)
	    modifyTime = datetime.datetime.fromtimestamp(os.path.getmtime(path))
	    createTime = datetime.datetime.fromtimestamp(os.path.getctime(path))
	    accessTime = datetime.datetime.fromtimestamp(os.path.getatime(path))

	    createDays = (currenttime-createTime).days;
	    modifyDays = (currenttime-modifyTime).days;
	    accessDays = (currenttime-accessTime).days;


	    if(createDays>daysNum and  modifyDays>daysNum  and accessDays>daysNum):
	        print(path+createTip+createTime.strftime('%Y-%m-%d %H:%M:%S')+modifyTip+modifyTime.strftime('%Y-%m-%d %H:%M:%S')+accessTip+accessTime.strftime('%Y-%m-%d %H:%M:%S'))
	        print(createDays,modifyDays,accessDays)
	    #    if(filename[0:2] == "~$"):
	    #    	os.remove(path)
	    #    	print("----------------------------------")