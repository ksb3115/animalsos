FROM gradle:7.4-jdk:11-alpine as builder
WORKDIR /home/jdk

COPY build/libs/*.jar app.jar

RUN npm -y install

EXPOSE 3000

CMD ["npm","run","start"]

ENTRYPOINT ["java", "-jar", "app.jar"]