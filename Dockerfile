FROM openjdk:17-jdk-alpine AS build

WORKDIR /app

COPY build.gradle settings.gradle gradlew /app/
COPY . /app/

RUN ./gradlew

FROM openjdk:17-jdk-alpine AS runtime

WORKDIR /app

COPY --from=build /app/ /app/

CMD ./gradlew build && java -jar app.jar
