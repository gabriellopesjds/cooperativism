FROM maven:3.6.3-openjdk-11
ARG JAR_FILE
WORKDIR /cooperativism/
COPY . .
RUN mvn clean package -Pdocker
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java -Djava.security.egd=file:/dev/../dev/urandom $JAVA_OPTS -jar /app.jar