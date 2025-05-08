WhiteDeer白鹿打卡系统-后端

Finished：
* 依赖项配置及初始化
* 调用人脸识别算法（已测试 存在魔法路径）
* 调用腾讯地图api定位（已测试 需要进一步优化到卫星ip双定位提高精度与准确性）

TODO:
* 用户相关服务
* 任务相关服务
* 团队相关服务
* 验证码及二维码服务

Caution:
* 服务器本身需要配置好Python环境（opencv）才能正确进行人脸识别与ip定位
  * 例如pip安装opencv，pil，request模块
