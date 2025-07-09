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
class FacultyControllerTest {

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
        baseUrl = "http://localhost:" + port + "/faculty";

        testFaculty = new Faculty();
        testFaculty.setName("Test Faculty");
        testFaculty.setColor("Blue");
    }

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertNotNull(studentController);
        Assertions.assertNotNull(facultyController);
    }

    @Test
    public void getFaculty() throws Exception {
        facultyController.createFaculty(testFaculty);

        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                baseUrl + "/" + testFaculty.getId(),
                Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testFaculty.getName(), response.getBody().getName());
    }


    @Test
    public void createFaculty() throws Exception {
        ResponseEntity<Faculty> response = restTemplate.postForEntity(
                baseUrl,
                testFaculty,
                Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(testFaculty.getName(), response.getBody().getName());
    }

    @Test
    public void editFaculty() throws Exception {
        String newName = "Updated Name";
        facultyController.createFaculty(testFaculty);
        testFaculty.setName(newName);

        ResponseEntity<Faculty> response = restTemplate.exchange(
                baseUrl,
                HttpMethod.PUT,
                new HttpEntity<>(testFaculty),
                Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(newName, response.getBody().getName());
    }

    @Test
    public void deleteFaculty() throws Exception {
        facultyController.createFaculty(testFaculty);

        ResponseEntity<Void> response = restTemplate.exchange(
                baseUrl + "/" + testFaculty.getId(),
                HttpMethod.DELETE,
                null,
                Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void findFacultyByColour() throws Exception {
        facultyController.createFaculty(testFaculty);

        ResponseEntity<Collection> response = restTemplate.getForEntity(
                baseUrl + "/colour?color=Blue",
                Collection.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }


    @Test
    public void findFacultyByString() throws Exception {
        facultyController.createFaculty(testFaculty);

        ResponseEntity<Collection> response = restTemplate.getForEntity(
                baseUrl + "/search?search=Test Faculty",
                Collection.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(response.getBody().isEmpty());
    }


    @Test
    public void getStudents() throws Exception {
        facultyController.createFaculty(testFaculty);

        testStudent = new Student();
        testStudent.setName("Test Student");
        testStudent.setAge(20);
        testStudent.setFaculty(testFaculty);

        studentController.createStudent(testStudent);

        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                baseUrl + "/students?facultyId=" + testFaculty.getId(),
                Student[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().length);

        Student returnedStudent = response.getBody()[0];
        assertEquals(testStudent.getId(), returnedStudent.getId());
        assertEquals("Test Student", returnedStudent.getName());
        assertEquals(20, returnedStudent.getAge());
        assertNotNull(returnedStudent.getFaculty());
        assertEquals(testFaculty.getId(), returnedStudent.getFaculty().getId());
    }
}