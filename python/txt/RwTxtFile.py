#!/usr/bin/env python 
# -*- coding: utf-8 -*-
#import os,os.path,datetime,time,sys


def lineOper(str):
    returnStr = '';
    if('pre: \n' == str):
        returnStr = ""+'\n';
    elif('pre: ' == str[0:5]):
        returnStr =  str[5:];   
    else:
	    returnStr = 'pre: '+str;
    return returnStr;    

lines = [];
with open('C:\\Users\\liangjy\\Desktop\\temp3.0.1', 'r',encoding = 'utf-8') as f:
    lines = f.readlines();
    #print(f.read())
    #for line in f.readlines():
    #    print(line.strip())

print(lines)
newlines=[]
for index,value in enumerate(lines):
	newlines.insert(index,lineOper(value))
print(newlines)


'''
#newLines = map(lineOper,lines);
with open('C:\\Users\\liangjy\\Desktop\\temp3.0.1', 'w',encoding = 'utf-8') as f:
    f.write(''.join(newlines))        
print('done')
'''