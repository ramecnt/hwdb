package pro.sky.hwdb.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pro.sky.hwdb.model.Faculty;
import pro.sky.hwdb.model.Student;
import pro.sky.hwdb.service.StudentServiceImpl;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentServiceImpl studentService;

    public StudentController(StudentServiceImpl studentService) {
        this.studentService = studentService;
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudentInfo(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student);
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @PutMapping
    public ResponseEntity<Student> editStudent(@RequestBody Student student) {
        Student foundStudent = studentService.editStudent(student);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/age")
    public ResponseEntity<Collection<Student>> findStudents(@RequestParam(required = false) int age) {
        if (age > 0) {
            return ResponseEntity.ok(studentService.findByAge(age));
        }
        return ResponseEntity.ok(Collections.emptyList());
    }

    @GetMapping("/ageBetween")
    public ResponseEntity<Collection<Student>> findByAgeBetween(@RequestParam(required = false) int min, @RequestParam(required = false) int max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(studentService.findByAgeBetween(min, max));
    }

    @GetMapping("/faculty")
    public ResponseEntity<Faculty> getFaculty(@RequestParam(required = false) long facultyId) {
        return ResponseEntity.ok(studentService.getFaculty(facultyId));
    }

    @GetMapping("/amount")
    public ResponseEntity<Integer> studentAmount() {
        return ResponseEntity.ok(studentService.studentAmount());
    }

    @GetMapping("/average_age")
    public ResponseEntity<Float> averageAge() {
        return ResponseEntity.ok(studentService.avgAge());
    }

    @GetMapping("/last_students")
    public ResponseEntity<Collection<Student>> lastStudents() {
        return ResponseEntity.ok(studentService.lastStudents());
    }

    @GetMapping("/starts_with_a")
    public ResponseEntity<Collection<Student>> startsWithWithA() {
        return ResponseEntity.ok(studentService.startsWithA());
    }

    @GetMapping("/average_age_stream")
    public ResponseEntity<Double> averageAgeStream() {
        return ResponseEntity.ok(studentService.avgAgeStream());
    }
}