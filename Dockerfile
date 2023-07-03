
#FROM maven:3.8.1-openjdk-19
FROM maven:3.8.4-openjdk-17 as builder
WORKDIR /CurrencyService
COPY . /CurrencyService
RUN mvn -f /CurrencyService/pom.xml clean install
#FROM maven:3.6.0-jdk-11-slim AS build
#WORKDIR /CurrencyService
#COPY /target/servicesnew-0.0.1-SNAPSHOT.jar .
#RUN mvn clean install
#RUN mvn dependency:resolve
#CMD ["java", "-jar", "ваше-приложение.jar"]
#######
#FROM maven:3.8.4-openjdk-17 as builder
#WORKDIR /CurrencyService
#COPY . /CurrencyService/.
#RUN mvn -f /CurrencyService/pom.xml clean package -Dmaven.test.skip=true