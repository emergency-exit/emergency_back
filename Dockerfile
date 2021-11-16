FROM adoptopenjdk/openjdk11

COPY . velog/app

WORKDIR velog/app

RUN chmod +x gradlew
RUN ./gradlew clean bootJar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "velog_backend/build/libs/velog_backend-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=local"]


