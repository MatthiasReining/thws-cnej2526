## Run & Build

see README-Quarkus.md

Start:

    mvn quarkus:dev

### Build jar

    mvn package
    java -jar target\quarkus-app\quarkus-run.jar

### Build Docker.jvm

see README-Quarkus.md AND Dockerfile.jvm

    ./mvnw package
    docker build -f src/main/docker/Dockerfile.jvm -t quarkus/students-jvm .
    docker run -i --rm -p 8080:8080 quarkus/students-jvm

### Build Docker.Native

see README-Quarkus.md AND Dockerfile.native

    ./mvnw package -Dnative
    docker build -f src/main/docker/Dockerfile.native -t quarkus/students-native .
    docker run -i --rm -p 8080:8080 quarkus/students-native
