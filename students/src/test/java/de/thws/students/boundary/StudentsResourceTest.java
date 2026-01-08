package de.thws.students.boundary;

import de.thws.students.control.StudentService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;

@QuarkusTest
class StudentsResourceTest {

    @Inject
    StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService.clear();
    }

    @AfterEach
    void tearDown() {
        studentService.clear();
    }

    @Test
    void testGetAllStudents() {
        given()
                .when().get("/students")
                .then()
                .statusCode(200)
                .body("$", hasSize(0));
    }

    @Test
    void testCreateStudent() {
        String requestBody = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "matriculationNumber": "1234567",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/students")
                .then()
                .statusCode(200)
                .body("id", notNullValue())
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("Doe"))
                .body("email", equalTo("john.doe@example.com"))
                .body("matriculationNumber", equalTo("1234567"))
                .body("enrollmentDate", equalTo("2024-01-15"));
    }

    @Test
    void testCreateStudentWithInvalidEmail() {
        String requestBody = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "invalid-email",
                    "matriculationNumber": "1234567",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/students")
                .then()
                .statusCode(400);
    }

    @Test
    void testCreateStudentWithInvalidMatriculationNumber() {
        String requestBody = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "matriculationNumber": "123",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/students")
                .then()
                .statusCode(400);
    }

    @Test
    void testCreateStudentWithMissingFields() {
        String requestBody = """
                {
                    "firstName": "John"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/students")
                .then()
                .statusCode(400);
    }

    @Test
    void testGetStudentById() {
        String requestBody = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "matriculationNumber": "1234567",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        Long id = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/students")
                .then()
                .statusCode(200)
                .extract().path("id");

        given()
                .when().get("/students/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()))
                .body("firstName", equalTo("John"));
    }

    @Test
    void testGetStudentByIdNotFound() {
        given()
                .when().get("/students/999")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetStudentByMatriculationNumber() {
        String requestBody = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "matriculationNumber": "1234567",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/students")
                .then()
                .statusCode(200);

        given()
                .when().get("/students/matriculation/1234567")
                .then()
                .statusCode(200)
                .body("matriculationNumber", equalTo("1234567"))
                .body("firstName", equalTo("John"));
    }

    @Test
    void testUpdateStudent() {
        String createBody = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "matriculationNumber": "1234567",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        Long id = given()
                .contentType(ContentType.JSON)
                .body(createBody)
                .when().post("/students")
                .then()
                .statusCode(200)
                .extract().path("id");

        String updateBody = """
                {
                    "firstName": "John",
                    "lastName": "Smith",
                    "email": "john.smith@example.com",
                    "matriculationNumber": "1234567",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when().put("/students/" + id)
                .then()
                .statusCode(200)
                .body("lastName", equalTo("Smith"))
                .body("email", equalTo("john.smith@example.com"));
    }

    @Test
    void testUpdateStudentNotFound() {
        String updateBody = """
                {
                    "firstName": "John",
                    "lastName": "Smith",
                    "email": "john.smith@example.com",
                    "matriculationNumber": "1234567",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(updateBody)
                .when().put("/students/999")
                .then()
                .statusCode(404);
    }

    @Test
    void testDeleteStudent() {
        String requestBody = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "matriculationNumber": "1234567",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        Long id = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when().post("/students")
                .then()
                .statusCode(200)
                .extract().path("id");

        given()
                .when().delete("/students/" + id)
                .then()
                .statusCode(204);

        given()
                .when().get("/students/" + id)
                .then()
                .statusCode(404);
    }

    @Test
    void testDeleteStudentNotFound() {
        given()
                .when().delete("/students/999")
                .then()
                .statusCode(404);
    }

    @Test
    void testGetAllStudentsWithData() {
        String requestBody1 = """
                {
                    "firstName": "John",
                    "lastName": "Doe",
                    "email": "john.doe@example.com",
                    "matriculationNumber": "1234567",
                    "enrollmentDate": "2024-01-15"
                }
                """;

        String requestBody2 = """
                {
                    "firstName": "Jane",
                    "lastName": "Smith",
                    "email": "jane.smith@example.com",
                    "matriculationNumber": "2345678",
                    "enrollmentDate": "2024-01-16"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody1)
                .when().post("/students")
                .then()
                .statusCode(200);

        given()
                .contentType(ContentType.JSON)
                .body(requestBody2)
                .when().post("/students")
                .then()
                .statusCode(200);

        given()
                .when().get("/students")
                .then()
                .statusCode(200)
                .body("$", hasSize(2));
    }
}
