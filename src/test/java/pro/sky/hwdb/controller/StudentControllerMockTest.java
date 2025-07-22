package pro.sky.hwdb.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.hwdb.model.Faculty;
import pro.sky.hwdb.model.Student;
import pro.sky.hwdb.repositories.StudentRepository;
import pro.sky.hwdb.service.StudentService.StudentServiceImpl;

import java.util.HashSet;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentRepository studentRepository;

    @MockitoBean
    private StudentServiceImpl studentService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Student testStudent = new Student();
    private final long id = 1L;
    private final String name = "Harry Potter";
    private final int age = 20;

    @BeforeEach
    void setUp() {
        testStudent.setId(id);
        testStudent.setName(name);
        testStudent.setAge(age);
        testStudent.setFaculty(new Faculty());
    }

    @Test
    void createStudent() throws Exception {
        when(studentService.addStudent(any(Student.class))).thenReturn(testStudent);

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));
    }

    @Test
    void getStudent() throws Exception {
        when(studentService.findStudent(anyLong())).thenReturn(testStudent);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }


    @Test
    void updateStudent() throws Exception {
        Student updatedStudent = new Student();
        updatedStudent.setId(id);
        updatedStudent.setName("Harry Potter Updated");
        updatedStudent.setAge(21);

        when(studentService.editStudent(any(Student.class))).thenReturn(updatedStudent);

        mockMvc.perform(MockMvcRequestBuilders.put("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedStudent)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry Potter Updated"))
                .andExpect(jsonPath("$.age").value(21));
    }

    @Test
    void deleteStudent() throws Exception {
        doNothing().when(studentService).deleteStudent(id);

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void findByAge() throws Exception {
        Student newStudent = new Student();
        newStudent.setId(2L);
        newStudent.setName("Hermione Granger");
        newStudent.setAge(20);
        newStudent.setFaculty(new Faculty());
        List<Student> students = List.of(
                testStudent,
                newStudent
        );
        when(studentService.findByAge(age)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/age")
                        .param("age", String.valueOf(age)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[1].name").value("Hermione Granger"));
    }

    @Test
    void findByAgeBetween() throws Exception {
        Student newStudent = new Student();
        newStudent.setId(2L);
        newStudent.setName("Ron Weasley");
        newStudent.setAge(21);
        newStudent.setFaculty(new Faculty());
        List<Student> students = List.of(
                testStudent,
                newStudent
        );
        when(studentService.findByAgeBetween(19, 22)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/ageBetween")
                        .param("min", "19")
                        .param("max", "22"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void getFacultyByStudentId() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setStudents(new HashSet<>());
        faculty.setColor("Red");
        faculty.setName("Gryffindor");
        when(studentService.getFaculty(id)).thenReturn(faculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/faculty").param("facultyId", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor"))
                .andExpect(jsonPath("$.color").value("Red"));
    }
}