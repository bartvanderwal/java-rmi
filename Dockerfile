# Bron: https://dzone.com/articles/multi-stage-docker-image-build-for-java-apps
# Geupdatet naar Java 11 (JDK en JRE) en switch van Gradle naar simpel javac
FROM openjdk:11 AS BUILD_IMAGE
ENV APP_HOME=/root/dev/chatjava/
RUN mkdir -p $APP_HOME/src/
WORKDIR $APP_HOME
COPY compile $APP_HOME
COPY . .
RUN ./compile

FROM openjdk:11-jre
WORKDIR /root/dev/chatjava
COPY --from=BUILD_IMAGE /root/dev/chatjava/target /root/dev/chatjava/target
COPY --from=BUILD_IMAGE /root/dev/chatjava/src/resources /root/dev/chatjava/src/resources
EXPOSE 1099
CMD ["java", "-cp", "target", "chatjava/ChatServerApp"]
