# 基础镜像
FROM eclipse-temurin:21.0.5_11-jdk-ubi9-minimal

# 设置环境变量，例如将工作目录设置为/app
ENV APP_HOME /app
WORKDIR $APP_HOME

# 指定JVM参数，例如最小堆大小、最大堆大小等
ENV JAVA_OPTS "-Xms512m -Xmx1024m"

COPY ksas-web/build/libs/ksas-web.jar $APP_HOME/app.jar

# 声明容器运行时监听的端口
EXPOSE 8080

# 指定容器启动时执行的命令，这里使用java -jar命令运行jar包，并附加JVM参数
CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
