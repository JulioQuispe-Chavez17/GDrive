FROM openjdk:11-jdk-slim
COPY "./target/bucket.jar" "app.jar"
EXPOSE 9090
ENTRYPOINT [ "java", "-jar", "app.jar" ]