FROM adoptopenjdk/openjdk11

COPY . velog/app

WORKDIR velog/app

RUN chmod +x gradlew
RUN ./gradlew clean bootJar

EXPOSE 9000

ENTRYPOINT ["java", "-jar", "velog_admin/build/libs/velog_admin-0.0.1-SNAPSHOT.jar", "--spring.profiles.active=local"]


