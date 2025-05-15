FROM openjdk:21-jdk-slim

WORKDIR /app

COPY target/Autocomplete_And_Spell_Checking-0.0.1-SNAPSHOT.jar app.jar

#RUN ./mvnw package -DskipTests

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]