FROM bellsoft/liberica-openjdk-alpine:11.0.2

COPY build/libs/*.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]