FROM gradle:7.5.1-jdk11 AS builder
COPY --chown=gradle:gradle . /src
WORKDIR /src
RUN gradle clean bootJar

FROM openjdk:11
EXPOSE 80
RUN mkdir /app
COPY --from=builder /src/build/libs/*.jar /app/oauth-backend.jar
ENTRYPOINT ["java", "-jar", "/app/oauth-backend.jar"]