FROM adoptopenjdk/openjdk11

ENV APP_PATH = /usr/src/app

WORKDIR $APP_PATH

COPY . $APP_PATH

RUN $APP_PATH/gradlew clean bootJar

ENTRYPOINT ["java", "-jar", "velog_backend/build/libs/velog_backend-0.0.1-SNAPSHOT.jar"]

