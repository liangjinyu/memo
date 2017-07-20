#!/usr/bin/env python 
# -*- coding: utf-8 -*-
import requests,json,datetime
from MD5 import md5U,md5

#url = 'http://openapi-test.you.163.com/channel/api.json'
url = 'http://openapi.you.163.com/channel/api.json'

headers = {'content-type': 'application/x-www-form-urlencoded'}

timestamp=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')
#params = {'method':'yanxuan.item.batch.get','timestamp':timestamp,'appKey':'bd8d8cd62fb5489586858161efcea388','itemIds':'10018002'}
params = {'method':'yanxuan.item.batch.get','timestamp':timestamp,'appKey':'3a95b0425b054bdf87ee75bc8da859d4','itemIds':'1071000'}

paramsStr = '' 
#for (k, v) in params.items():      
#	paramsStr += (k+'='+v)

for k in sorted(params.keys()):
	paramsStr+= (k+'='+params[k])
#appSecret='db4c3fe3515543bfb1cf6beb6f3e3d56'
appSecret='4a572ede841949348651e931a37a6096'
waitMd5Str = appSecret + paramsStr+ appSecret;
params['sign']=md5U(waitMd5Str)
print(params)
print(headers)
print(url)

res = requests.post(url, data = params,headers=headers)

print(res.status_code)
#print(res.url)
print(res.text)
#response = json.loads(res.text)


