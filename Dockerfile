FROM openjdk:17-jdk-alpine AS build

WORKDIR /app

COPY . /app/

RUN ./gradlew

FROM openjdk:17-jdk-alpine AS runtime

RUN apk update && apk add --no-cache make protobuf-dev protoc wget curl nano bash
RUN wget -q -O /etc/apk/keys/sgerrand.rsa.pub https://alpine-pkgs.sgerrand.com/sgerrand.rsa.pub &&
wget https://github.com/sgerrand/alpine-pkg-glibc/releases/download/2.35-r1/glibc-2.35-r1.apk &&
apk add glibc-2.35-r1.apk && ./gradlew


WORKDIR /app

COPY --from=build /app/ /app/

CMD ./gradlew build && java -jar app.jar
