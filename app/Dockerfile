FROM maven:3.8.6-eclipse-temurin-17-alpine as builder

COPY ./pom.xml ./app/pom.xml
COPY ./src ./app/src

RUN mvn --batch-mode -f /app/pom.xml clean package

RUN java -Djarmode=layertools -jar app/target/*.jar extract
RUN addgroup appgroup; adduser --ingroup appgroup --disabled-password app -u 1000 -g 100

RUN mkdir -p logs
RUN chown app logs
RUN chmod -R 777 logs

FROM eclipse-temurin:17-jre-alpine
RUN addgroup appgroup; adduser --ingroup appgroup --disabled-password app -u 1000 -g 100
USER app

COPY --from=builder dependencies/ ./
RUN true
COPY --from=builder snapshot-dependencies/ ./
RUN true
COPY --from=builder spring-boot-loader/ ./
RUN true
COPY --from=builder application/ ./
RUN true
COPY --from=builder logs/ ./

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]