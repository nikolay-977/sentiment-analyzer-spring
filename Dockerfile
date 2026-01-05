FROM amazoncorretto:17-alpine AS build

WORKDIR /app

RUN apk add --no-cache bash curl

COPY gradlew .
COPY gradle gradle
RUN chmod +x ./gradlew
COPY build.gradle .
COPY settings.gradle .

COPY src src

RUN ./gradlew bootJar -x test --no-daemon

FROM amazoncorretto:17-alpine

WORKDIR /app

COPY --from=build /app/build/libs/sentiment-analyzer.jar app.jar

RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
