#!/usr/bin/env python3.6
# -*- coding: utf-8 -*-
import math
import datetime
# judge is prime
def isPrime(num):
    if num < 2:
    	return False
    elif num == 2 or num == 3 or num == 5:
        return True
    elif num % 2 == 0 or num % 3 == 0 or num % 5 == 0:
        return False
    else:    
        judgeEnd = math.sqrt(num)+1
        i=7
        while i<judgeEnd:
        	if num% i == 0:
        		return False
        	else:
        	   i=i+2
        return True
maxNum = int(input('input your range:'))
timeBegin = datetime.datetime.now().microsecond
n = 1
sum = 0

while n < maxNum:
    if(isPrime(n)):
        sum = sum + 1
    n = n+1;
print("prime number total count = "+str(sum))
timeEnd = datetime.datetime.now().microsecond
print("exhost time is "+ str(timeEnd-timeBegin))
