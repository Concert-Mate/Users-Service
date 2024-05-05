FROM ubuntu:latest

WORKDIR /app

COPY . /app/

RUN ./gradlew

CMD ./gradlew build && java -jar /app/build/libs/app.jar
