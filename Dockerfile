FROM --platform=linux/amd64 openjdk:21
EXPOSE 8080
COPY backend/target/TrackeR-Forage.jar TrackeR-Forage.jar
ENTRYPOINT ["java", "-jar", "TrackeR-Forage.jar"]
