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
import pro.sky.hwdb.repositories.FacultyRepository;
import pro.sky.hwdb.service.FacultyServiceImpl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
public class FacultyControllerMockTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacultyRepository facultyRepository;

    @MockitoBean
    private FacultyServiceImpl facultyService;

    @Autowired
    private ObjectMapper objectMapper;

    private final Faculty testFaculty = new Faculty();
    private final long id = 1L;
    private final String name = "Gryffindor";
    private final String color = "Red";

    @BeforeEach
    void setUp() {
        testFaculty.setId(id);
        testFaculty.setName(name);
        testFaculty.setColor(color);
    }

    @Test
    void createFaculty() throws Exception {
        when(facultyService.addFaculty(any(Faculty.class))).thenReturn(testFaculty);

        mockMvc.perform(MockMvcRequestBuilders.post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testFaculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.color").value(color));
    }

    @Test
    void getFaculty() throws Exception {
        when(facultyService.findFaculty(1L)).thenReturn(testFaculty);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name));
    }

    @Test
    void updateFaculty() throws Exception {
        Faculty updatedFaculty = new Faculty();
        updatedFaculty.setId(1L);
        updatedFaculty.setName("Gryffindor Updated");
        updatedFaculty.setColor("Blue");
        updatedFaculty.setStudents(new HashSet<>());

        when(facultyService.editFaculty(any(Faculty.class))).thenReturn(updatedFaculty);

        mockMvc.perform(MockMvcRequestBuilders.put("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedFaculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gryffindor Updated"))
                .andExpect(jsonPath("$.color").value("Blue"));
    }

    @Test
    void deleteFaculty() throws Exception {
        doNothing().when(facultyService).deleteFaculty(1L);

        mockMvc.perform(delete("/faculty/{id}", 1L))
                .andExpect(status().isOk());
    }

    @Test
    void findByColor() throws Exception {
        Faculty newFaculty = new Faculty();
        newFaculty.setColor(color);
        newFaculty.setName("Slitherine");
        newFaculty.setStudents(new HashSet<>());
        List<Faculty> faculties = List.of(
                testFaculty,
                newFaculty
        );
        when(facultyService.findByColor(color)).thenReturn(faculties);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/color").param("color", color))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[1].name").value("Slitherine"));
    }

    @Test
    void findByNameOrColor() throws Exception {
        when(facultyService.findByString(name))
                .thenReturn(List.of(testFaculty));

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/search")
                        .param("search", "Gryffindor"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    void getFacultyStudents() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("Harry Potter");
        student1.setAge(20);
        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("Hermione Granger");
        student2.setAge(20);
        Set<Student> students = Set.of(
                student1,
                student2
        );
        testFaculty.setStudents(students);

        when(facultyService.getStudents(id)).thenReturn(students);

        mockMvc.perform(MockMvcRequestBuilders.get("/faculty/students").param("facultyId", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name").exists());
    }
}
