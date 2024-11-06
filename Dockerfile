FROM adoptopenjdk/openjdk11:ubi as build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

RUN ./mvnw dependency:go-offline -B

COPY src src

RUN ./mvnw package -DskipTests
RUN mkdir -p  target/dependency && (cd target/dependency; jar -xf ../*.jar)


### Stage 2: A minimal docker image with command to run the app
FROM adoptopenjdk/openjdk11:ubi
ARG DEPENDENCY=/app/target/dependency

#copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-cp","app:app/lib/*","com.example.backendproject.BackEndProjectApplication"]