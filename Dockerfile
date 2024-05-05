FROM openjdk:17-jdk-alpine AS build

WORKDIR /app

COPY . /app/

RUN ./gradlew

FROM openjdk:17-jdk-alpine AS runtime

RUN apk update && apk add --no-cache make protobuf-dev protoc

WORKDIR /app

COPY --from=build /app/ /app/

CMD ./gradlew build && java -jar app.jar
