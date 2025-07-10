package pro.sky.hwdb.service;

import org.springframework.stereotype.Service;
import pro.sky.hwdb.model.Faculty;
import pro.sky.hwdb.model.Student;
import pro.sky.hwdb.repositories.StudentRepository;

import java.util.Collection;

@Service
public class StudentService implements StudentServiceImpl {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public Student findStudent(long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public Student editStudent(Student student) {
        return studentRepository.save(student);
    }

    @Override
    public void deleteStudent(long id) {
        studentRepository.deleteById(id);
    }

    @Override
    public Collection<Student> findByAge(int age) {
        return studentRepository.findStudentsByAge(age);
    }

    @Override
    public Collection<Student> findByAgeBetween(int min, int max) {
        return studentRepository.findStudentsByAgeBetween(min, max);
    }

    @Override
    public Faculty getFaculty(long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow();
        return student.getFaculty();
    }

    @Override
    public Integer studentAmount() {
        return studentRepository.studentAmount();
    }

    @Override
    public Float avgAge() {
        return studentRepository.avgAge();
    }

    @Override
    public Collection<Student> lastStudents() {
        return studentRepository.lastStudents();
    }
}