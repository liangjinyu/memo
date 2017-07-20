#!/usr/bin/env python 
# -*- coding: utf-8 -*-

import hashlib
def md5(str):
    m = hashlib.md5()   
    str=str.encode('utf-8')
    m.update(str)
    return m.hexdigest()


def md5U(str):
    m = hashlib.md5()   
    str=str.encode('utf-8')
    m.update(str)
    return m.hexdigest().upper()
#str='asdf21dfas546we54t651rt3u21yk2nb1m65yh'
#print(md5U(str))


