FROM maven:3 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -B -Dmaven.repo.local=.m2/repository -f /usr/src/app/pom.xml  package

FROM openjdk:11
COPY --from=build /usr/src/app/target /usr/app
RUN ls -la /usr/app
EXPOSE 80
ENTRYPOINT ["/bin/sh", "-c", "java -jar /usr/app/*.jar"]
