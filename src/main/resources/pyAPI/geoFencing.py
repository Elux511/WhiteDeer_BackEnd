import sys

import requests


#腾讯API key
api_key = 'RT4BZ-EGQCT-JH7XZ-V62B6-P5QI5-G3B62'


#获取设备当前经纬度(腾讯api)
#返回纬度和经度
def getCurrentLocation():
    #获取当前设备的公网地址
    ip_response = requests.get("https://ipinfo.io/json")
    if ip_response.status_code != 200:
        raise Exception("无法获取IP地址")
    ip_address = ip_response.json()["ip"]

    # 腾讯地图IP定位API的URL
    url = "https://apis.map.qq.com/ws/location/v1/ip"

    # 请求参数
    params = {
        'ip': ip_address,
        'key': api_key
    }

    # 发送GET请求
    location_response = requests.get(url, params=params)

    # 解析响应
    if location_response.status_code == 200:    #说明请求成功
        result = location_response.json()
        if result["status"] == 0:
            location = result["result"]["location"]
            latitude = location["lat"]  #纬度
            longitude = location["lng"] #经度
            return latitude, longitude
        else:
            raise Exception(f"错误: {result['message']}")
    else:       #请求失败
        raise Exception(f"请求失败，状态码: {location_response.status_code}")

if __name__ == "__main__":
    latitude, longitude = getCurrentLocation()
    print(f"{latitude}")
    print(f"{longitude}")