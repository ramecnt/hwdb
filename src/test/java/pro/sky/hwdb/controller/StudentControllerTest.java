package pro.sky.hwdb.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pro.sky.hwdb.model.Faculty;
import pro.sky.hwdb.model.Student;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private FacultyController facultyController;

    @Autowired
    private TestRestTemplate restTemplate;

    private String baseUrl;
    private Student testStudent;
    private Faculty testFaculty;

    @BeforeEach
    public void setUp() {
        baseUrl = "http://localhost:" + port + "/student";

        testFaculty = new Faculty();
        testFaculty.setName("Test Faculty");
        testFaculty.setColor("Blue");

        testStudent = new Student();
        testStudent.setName("Test Student");
        testStudent.setAge(20);
        testStudent.setFaculty(testFaculty);
    }

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertNotNull(studentController);
        Assertions.assertNotNull(facultyController);
    }

    @Test
    public void getStudent() throws Exception {
        facultyController.createFaculty(testFaculty);
        studentController.createStudent(testStudent);

        ResponseEntity<Student> response = restTemplate.getForEntity(
                baseUrl + "/" + testStudent.getId(),
                Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testStudent.getName(), response.getBody().getName());
    }


    @Test
    public void createStudent() throws Exception {
        facultyController.createFaculty(testFaculty);
        ResponseEntity<Student> response = restTemplate.postForEntity(
                baseUrl,
                testStudent,
                Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testStudent.getName(), response.getBody().getName());
    }

    @Test
    public void editStudent() throws Exception {
        String newName = "Updated Name";
        facultyController.createFaculty(testFaculty);
        studentController.createStudent(testStudent);
        testStudent.setName(newName);

        ResponseEntity<Student> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(testStudent),
                Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(newName, response.getBody().getName());
    }

    @Test
    public void deleteStudent() throws Exception {
        facultyController.createFaculty(testFaculty);
        studentController.createStudent(testStudent);

        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/" + testStudent.getId(),
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findStudentsByAge() throws Exception {
        facultyController.createFaculty(testFaculty);
        studentController.createStudent(testStudent);

        ResponseEntity<Collection> response = restTemplate.getForEntity(
                baseUrl + "/age?age=20",
                Collection.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }


    @Test
    public void findByAgeBetween() throws Exception {
        facultyController.createFaculty(testFaculty);
        studentController.createStudent(testStudent);

        ResponseEntity<Collection> response = restTemplate.getForEntity(
                baseUrl + "/ageBetween?min=18&max=22",
                Collection.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }


    @Test
    public void getFaculty() throws Exception {
        facultyController.createFaculty(testFaculty);
        studentController.createStudent(testStudent);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                baseUrl + "/faculty?facultyId=" + testStudent.getId(),
                Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testFaculty.getName(), response.getBody().getName());
    }
}