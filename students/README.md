# Students – Quarkus / Jakarta EE Example Project

## Run & Build

See `README-Quarkus.md` for general Quarkus setup and prerequisites.

### Start (Dev Mode)

```bash
mvn quarkus:dev
```

---

### Build JAR

```bash
mvn package
java -jar target/quarkus-app/quarkus-run.jar
```

---

### Build Docker (JVM)

See `README-Quarkus.md` and `Dockerfile.jvm`.

```bash
./mvnw package
docker build -f src/main/docker/Dockerfile.jvm -t thws/students-jvm .
docker run -i --rm -p 8080:8080 thws/students-jvm
```

Start docker and use psql also within docker on HOST machine:

docker run -i --rm -p - QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://host.docker.internal:5432/mydb 8080:8080 thws/students-jvm

## HOST IP = host.docker.internal

### Build Docker (Native)

See `README-Quarkus.md` and `Dockerfile.native`.

On **Windows**, it is strongly recommended to use **WSL2 with GraalVM installed inside WSL**.

```bash
./mvnw package -Dnative
docker build -f src/main/docker/Dockerfile.native -t thws/students-native .
docker run -i --rm -p 8080:8080 thws/students-native
```

---

## Application Logic

**GitHub Copilot prompt used for backend generation:**

```text
Add CRUD classes for "Students" to the students project with a JAX-RS interface / Jakarta EE.
Storage should be managed by a static map (test purposes only).
The structure and design should follow the "Adam Bien" style.
Use BCE package structure with the root package "de.thws.students".
Use DTO records for the JAX-RS interface.
JAX-RS responses should return DTO objects only (no Response class).

Add suitable Bean Validation annotations to the DTOs.

Furthermore, build suitable test cases.
```

---

## Package Structure

The project follows the **BCE (Boundary–Control–Entity)** pattern:

- https://bce.design/

---

## JAX-RS

See `*Resource` classes.

Guidelines:

- Use **DTOs** for all endpoints
- Example:
  - `CreateStudentDTO` → create
  - `StudentDTO` → read
- This allows different validation rules per use case

Bean Validation examples:

- `@NotNull`
- `@NotEmpty`
- etc.

---

## Frontend

Static resources are served via Quarkus:

- https://quarkus.io/guides/web#serving-static-resources

**GitHub Copilot prompt used for frontend generation:**

```text
Generate an HTML application (website) as a CRUD page for Student objects.
The page should support listing, creating, editing, and deleting students.

Use:
- lit as the JS framework/library combined with Vanilla JS (ES6+)
- ES6 modules and JS classes
- Web Components based on LitElement

Interaction:
- Use fetch API to communicate with backend JAX-RS services
- Work directly with JSON (no DTO placeholders required in JS)

The page should work standalone, but be placed under:
src/main/resources/META-INF/resources

Structure:
- One HTML file
- Separate JS files (one file per class)
- Use the latest Bootstrap CSS via external CDN

Create the required folders and files directly.
```

_(Result is usable but not optimal; the prompt can be further refined.)_

---

## JPA – Jakarta Persistence API

See different examples for `Student`.

---

### Infrastructure

- PostgreSQL and pgAdmin are managed via `docker-compose.yml`
- Start infrastructure:

```bash
docker compose up -d
```

- pgAdmin available at:
  - http://localhost:5050

SQL examples (via pgAdmin):

- Primary keys and sequences
- Transactions:
  - `BEGIN`
  - `ROLLBACK`
  - `COMMIT`

---

### Entity Beans

Example: `Student`

- Initialization: `init.sql`
- Configuration: `application.properties`

---

### JPA Examples

Entities:

- `Course`
- `Student`
- `CourseParticipant`

### Navigation

- Course → CourseParticipant → Student
- Student → CourseParticipant → Course

Relationship direction depends on context:

- Uni-directional
- Bi-directional

Reference:

- https://jakarta.ee/learn/docs/jakartaee-tutorial/current/persist/persistence-intro/persistence-intro.html#_direction_in_entity_relationships

---

### Important JPA Development Notes

During development, **always inspect the generated SQL (DDL & DML)**:

- Table structures may differ from expectations  
  (e.g. `n:m` instead of `1:n` when `@JoinColumn` is missing)
- Generated queries are often not optimal

Example:  
The following SQL join is **more efficient** than what Hibernate may generate by default:

```sql
SELECT *
FROM student s
JOIN courseparticipant cp ON s.id = cp.student_id
JOIN course c ON c.id = cp.course_id
WHERE cp.course_id = 1;
```

---

### Advanced Example: Panache

See `LogData.java`.

Here, entity beans **extend `PanacheEntity`**, which provides:

- Simplified CRUD operations
- Reduced boilerplate
- Built-in EntityManager access

Reference:

- https://quarkus.io/guides/hibernate-orm-panache

# Micro Profile

## Config

Example call with docker:

docker run -i --rm -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://host.docker.internal:5432/mydb -e THWS_LOGGER=SPECIAL -p 8080:8080 thws/students-jvm

use environment vairalbe THWS_LOGGER...
