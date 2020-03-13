## SpringBoot v2.2.4 + ElasticSearch v6.8.6


##### &nbsp;&nbsp;&nbsp;&nbsp;自己服务器的搭建得 ElasticSearch，没有用云服务。

---
- ElasticSearch官网下载资源包

1.上传资源包，解压

2.建立用户，组，给予用户解压文件目录权限（root用户不能启动，因为引擎可以穿脚本威胁服务器安全）

3.修改Linux内核大小

4.打开防火墙 9200 9300 端口

4.解压文件bin目录下./sea...... 启动

5.项目中引入依赖，配置链接，就可以根据情况开始搜索引擎之旅了~