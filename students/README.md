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

# Frontend

https://quarkus.io/guides/web#serving-static-resources

github Copilot Prompt:

```

generate a HTML application (web site) as CRUD page for students object. There should be a list, the option to edit and delete and student and to remove a student entity.
The page should use lit as JS framework / library combinde with Vanilla JS (ES6+). ES6 app managed in JS classes.
The interaction should done via ES fetch to the backend JAX-RS services.
But in general the page should work standalone. But nevertheless it should be placed in Quarkus resources\META-INF as static web resources.
The JS App should be sepearted in HTML file, JS files (one file per class). For CSS use the latest Bootstrap CSS library (external linked).

Create the folders and files directly.
```

(Result is not so nice... prompt should be optimized...)
