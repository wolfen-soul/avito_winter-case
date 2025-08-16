FROM docker.io/library/gradle:8.13.0-jdk17 AS builder
WORKDIR /app
COPY . .
RUN gradle clean build --no-daemon

FROM docker.io/bellsoft/liberica-openjre-alpine:17
RUN addgroup -g 1001 -S jre && adduser -S jre -u 1001 
WORKDIR /app
COPY --from=builder --chown=jre:jre /app/build/libs/avito-0.1a.jar ./app.jar
RUN rm -rf /var/cache/apk/* /tmp/*

USER jre
CMD ["java", "-jar", "app.jar"]
