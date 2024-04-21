FROM openjdk:17-jdk-alpine

RUN apk add --no-cache bash

WORKDIR /app

COPY gradlew .
COPY gradle ./gradle

COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN chmod +x gradlew
RUN ./gradlew test

EXPOSE 8080

CMD ["./gradlew", "bootRun"]
