FROM openjdk:11.0.5-jre
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java -Djava.security.egd=file:/dev/../dev/urandom $JAVA_OPTS -jar /app.jar