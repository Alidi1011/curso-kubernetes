FROM openjdk:11  as builder

WORKDIR /app/msvc-cursos

COPY ./pom.xml /app
COPY ./springboot-msvc-cursos/.mvn ./.mvn
COPY ./springboot-msvc-cursos/mvnw .
COPY ./springboot-msvc-cursos/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline

COPY ./springboot-msvc-cursos/src ./src

RUN ./mvnw clean package -DskipTests

FROM openjdk:11

WORKDIR /app
RUN mkdir ./logs
COPY --from=builder /app/msvc-cursos/target/springboot-msvc-cursos-0.0.1-SNAPSHOT.jar .
EXPOSE 8002

ENTRYPOINT ["java", "-jar", "springboot-msvc-cursos-0.0.1-SNAPSHOT.jar"]