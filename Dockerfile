FROM ubuntu:latest

WORKDIR /app

COPY . /app/

RUN apt-get update && \
    apt-get install -y protobuf-compiler openjdk-17-jd && \
    rm -rf /var/lib/apt/lists/*

RUN ./gradlew

CMD ./gradlew build && java -jar /app/build/libs/app.jar
