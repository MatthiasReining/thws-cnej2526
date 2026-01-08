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
    docker build -f src/main/docker/Dockerfile.jvm -t thws/students-jvm .
    docker run -i --rm -p 8080:8080 thws/students-jvm

### Build Docker.Native

see README-Quarkus.md AND Dockerfile.native

On Windows, best using WSL2 with installed GraalVM on WSL

    ./mvnw package -Dnative
    docker build -f src/main/docker/Dockerfile.native -t thws/students-native .
    docker run -i --rm -p 8080:8080 thws/students-native

# Application Logic

github Copilot Prompt:

```
Add CRUD classes for "Students" to the students project with an JAX-RS interface / JakartaEE.
Storage should be managed just by a static map (only for test purpose).
The structure and the design should follow the "Adam Bien"-Style.
Package structure BCE with the "root" package "de.thws.students".
Use DTO recrods for the JAX-RS interface.
The Response of JAX-RS should be DTOs (objects); no Response class.

(For the DTOs use a useful BeanValidation.)

Furthermore build suitable test cases
```
