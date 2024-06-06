FROM eclipse-temurin:17.0.6_10-jre-alpine
RUN mkdir /opt/app
COPY target/holamundo-docker-0.0.1-SNAPSHOT.jar /opt/app
CMD ["java", "-jar", "/opt/app/holamundo-docker-0.0.1-SNAPSHOT.jar"]
