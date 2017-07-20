#!/usr/bin/env python3.6
# -*- coding: utf-8 -*-
#name = input('Please input your name:')
#print('hello',name)
#print('''asdf
#你好
#asdf''')

#sa=72
#s2=85
#r=(85-72)* 100/72
#print('%.1f'%r)
height=input('input your height:')
height=float(height)
weight=input('input your weight:')
weight=float(weight)
result=weight/(height*height)
print('your index is ',result)
if result < 18.5:
   print('过轻')
elif result < 25:
   print('正常')
elif result < 28:
   print('过重')
elif result < 32:
   print('肥胖')
else:
    print('严重肥胖')