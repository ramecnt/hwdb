package pro.sky.hwdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.hwdb.model.Faculty;
import pro.sky.hwdb.model.Student;
import pro.sky.hwdb.repositories.StudentRepository;

import java.util.Collection;

@Service
public class StudentService implements StudentServiceImpl {
    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        logger.info("Add student {}", student);
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        logger.info("Find student with id {}", id);
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student editStudent(Student student) {
        logger.info("Edit student {}", student);
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        logger.info("Delete student with id {}", id);
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> findByAge(int age) {
        logger.info("Find student by age {}", age);
        return studentRepository.findStudentsByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        logger.info("Find student by age between {} and {}", min, max);
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    @Override
    public Faculty getFaculty(long id) {
        logger.info("Get faculty of the student with id {}", id);
        Student student = studentRepository.findById(id)
                .orElseThrow();
        return student.getFaculty();
    }

    @Override
    public Integer studentAmount() {
        logger.info("Get student amount");
        return studentRepository.studentAmount();
    }

    @Override
    public Float avgAge() {
        logger.info("Get student avg age");
        return studentRepository.avgAge();
    }

    @Override
    public Collection<Student> lastStudents() {
        logger.info("Get 5 last students");
        return studentRepository.lastStudents();
    }
}