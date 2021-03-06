#!/usr/bin/env python 
# -*- coding: utf-8 -*-
import requests,json,datetime
from MD5 import md5U,md5

url = 'https://aliuat.memedai.cn/merchandise-center-web/wyyx/callback'
headers = {'content-type': 'application/x-www-form-urlencoded'}

#params = {'method':'yanxuan.item.id.batch.get','appKey':'bd8d8cd62fb5489586858161efcea388','sign':'E963E8E38CDF3841220E79BEB9469618','timestamp':'2017-06-15 14:21:37'}
timestamp=datetime.datetime.now().strftime('%Y-%m-%d %H:%M:%S')

# "skuTransfer":["{\"id\":\"1505128559260|485623\",\"operateTime\":1505128559260,\"skuTransfers\":[{\"skuId\":1124050,\"transferCount\":-5}]}"],

params = {'method':'yanxuan.notification.inventory.count.changed','timestamp':timestamp,'appKey':'bd8d8cd62fb5489586858161efcea388','skuTransfer':'{\"id\":\"1505128559260|485623\",\"operateTime\":1505128559260,\"skuTransfers\":[{\"skuId\":1124050,\"transferCount\":5}]}'}

paramsStr = '' 
#for (k, v) in params.items():      
#	paramsStr += (k+'='+v)

for k in sorted(params.keys()):
	paramsStr+= (k+'='+params[k])

appSecret='db4c3fe3515543bfb1cf6beb6f3e3d56'
waitMd5Str = appSecret + paramsStr+ appSecret;
params['sign']=md5U(waitMd5Str)
print(params)
res = requests.post(url, data = params,headers=headers)

print(res.status_code)
#print(res.url)
print(res.text)
#response = json.loads(res.text)

