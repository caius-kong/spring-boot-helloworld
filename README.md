# spring-boot-helloworld

配置分为dev/prod环境 - 开发/生产环境

生产环境中 (mac系统为例)
首次部署项目，可能出现日志错误：日志文件找不到！
原因：没有在/var/log下创建文件的权限
解决：终端运行==> $ sudo chmod -R 777 /var/log