FROM openjdk:17-jdk-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY gradle /app/gradle

RUN ./gradlew

FROM openjdk:17-jdk-alpine AS runtime

WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build .gradlew .gradlew

CMD ./gradlew build && java -jar app.jar
