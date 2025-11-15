# syntax=docker/dockerfile:1
ARG MAVEN_IMG=maven:3.9.9-eclipse-temurin-21
ARG JRE_IMG=eclipse-temurin:21-jre

FROM ${MAVEN_IMG} AS deps
WORKDIR /src
COPY build/maven/settings.xml /root/.m2/settings.xml
# Copy only POMs to warm dependency cache
COPY pom.xml ./
COPY gateway-service/pom.xml ./gateway-service/pom.xml
COPY auth-service/pom.xml ./auth-service/pom.xml
COPY user-service/pom.xml ./user-service/pom.xml
COPY details-service/pom.xml ./details-service/pom.xml
COPY invoice-service/pom.xml ./invoice-service/pom.xml
COPY activity-service/pom.xml ./activity-service/pom.xml
COPY hunted-service/pom.xml ./hunted-service/pom.xml
COPY session-service/pom.xml ./session-service/pom.xml
RUN mvn -B -q -s /root/.m2/settings.xml -DskipTests -U \
    -Dmaven.wagon.http.retryHandler.count=10 \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.http.timeout=60000 \
    dependency:go-offline

FROM ${MAVEN_IMG} AS build
ENV MAVEN_OPTS="-Xmx1g"
WORKDIR /src
COPY . .
COPY --from=deps /root/.m2 /root/.m2
RUN mvn -B -q -s /root/.m2/settings.xml -DskipTests -U \
    -Dmaven.wagon.http.retryHandler.count=10 \
    -Dmaven.wagon.http.pool=false \
    -Dmaven.wagon.http.timeout=60000 \
    -pl details-service -am package

FROM ${JRE_IMG}
WORKDIR /app
COPY --from=build /src/details-service/target/*.jar app.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","/app/app.jar"]
