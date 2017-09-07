#!/usr/bin/env python 
# -*- coding: utf-8 -*-
import requests,json,logging

#from urllib.request import urlopen

logger = logging.getLogger("loggingmodule.NomalLogger")  
handler = logging.FileHandler("/Users/jinyuliang/repo/memo/python/py.log")  
formatter = logging.Formatter("[%(levelname)s][%(funcName)s][%(asctime)s]%(message)s")  
handler.setFormatter(formatter)  
logger.addHandler(handler)  
logger.setLevel(logging.DEBUG)


#curl -H "Content-Type: application/json" -X POST  --data '{"pageNo":2}' https://aliuat.memedai.cn/merchandise-center-web/merchandise/getMerchandise

url = 'https://aliuat.memedai.cn/merchandise-center-web/merchandise/getMerchandise'
queryCondition={"pageNo":2}
headers = {'content-type': 'application/json'}
res = requests.post(url, data = json.dumps(queryCondition),headers=headers)
#print(res.status_code)
#print(res.url)
#print(res.text)
response = json.loads(res.text)
print(response['code'])
print(response['content']['merchandises'][0]['merchandiseName'])

print(type(response['content']['merchandises'][0]))

logger.info(response)








#print(res.text, '\n{}\n'.format('*'*79), res.encoding)

#for line in urlopen('http://www.baidu.com'):
#    line = line.decode('utf-8')  # Decoding the binary data to text.
#    print(line)
