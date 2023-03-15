# Bron: https://dzone.com/articles/multi-stage-docker-image-build-for-java-apps
# Geupdatet naar Java 11 (JDK en JRE) en switch van Gradle naar simpel javac
FROM openjdk:11 AS BUILD_IMAGE
ENV APP_HOME=/root/dev/chat-java/
RUN mkdir -p $APP_HOME/src/
WORKDIR $APP_HOME
COPY compile $APP_HOME
COPY . .
RUN ./compile

FROM openjdk:11-jre
WORKDIR /root/
COPY --from=BUILD_IMAGE /root/dev/chat-java/target/* .
EXPOSE 1099
CMD ./run-server