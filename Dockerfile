FROM ubuntu:latest

WORKDIR /app

COPY . /app/

RUN apt-get update && \
    apt-get install -y protobuf-compiler

RUN ./gradlew

CMD ./gradlew build && java -jar /app/build/libs/app.jar
