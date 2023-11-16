FROM openjdk:11

WORKDIR /app/msvc-usuarios

COPY ./pom.xml /app
COPY ./springboot-msvc-usuarios/.mvn ./.mvn
COPY ./springboot-msvc-usuarios/mvnw .
COPY ./springboot-msvc-usuarios/pom.xml .

RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot.repackage.skip && rm -r ./target/
#RUN ./mvnw dependency:go-offline

COPY ./springboot-msvc-usuarios/src ./src

RUN ./mvnw clean package -DskipTests

EXPOSE 8001

ENTRYPOINT ["java", "-jar", "./target/springboot-msvc-usuarios-0.0.1-SNAPSHOT.jar"]