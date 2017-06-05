# 基础镜像：仓库是java8
FROM java:8
# 当前镜像的维护者和联系方式
MAINTAINER kongyunhui 1032316751@qq.com
# 可选。如果你的应用程序需要写文件系统，例如日志（内嵌tomcat默认工作路径/tmp）。VOLUME将/tmp挂载在宿主机的/var/lib/docker目录下的一个临时文件
VOLUME /tmp
# 将打包好的spring程序拷贝到容器中的指定位置
ADD spring-boot-helloworld-1.0-SNAPSHOT.jar app.jar
# 容器启动时需要执行的命令
RUN bash -c 'touch /app.jar'
# 执行项目 app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]