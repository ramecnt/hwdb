package pro.sky.hwdb.service.StudentService;

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

    private final Object waitObject = new Object();

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

    @Override
    public Collection<Student> startsWithA() {
        logger.info("Get students starts with a student");
        Collection<Student> allStudents = studentRepository.findAll();
        return allStudents.stream().filter(s -> s.getName().toUpperCase().startsWith("A")).sorted().toList();
    }

    @Override
    public Double avgAgeStream() {
        logger.info("Get student avg age using stream stream");
        return studentRepository.findAll().stream().mapToDouble(Student::getAge).average().orElseThrow();
    }

    @Override
    public void printParallel() {
        logger.info("print student names parallel");
        System.out.println(findStudent(1).getName());
        System.out.println(findStudent(2).getName());

        new Thread(() -> {
            System.out.println(findStudent(3).getName());
            System.out.println(findStudent(4).getName());
        }).start();

        new Thread(() -> {
            System.out.println(findStudent(5).getName());
            System.out.println(findStudent(6).getName());
        }).start();
    }

    @Override
    public void printSynchronized() {
        logger.info("print student names synchronized");
        print(findStudent(1).getName());
        print(findStudent(2).getName());

        new Thread(() -> {
            print(findStudent(3).getName());
            print(findStudent(4).getName());
        }).start();

        new Thread(() -> {
            print(findStudent(5).getName());
            print(findStudent(6).getName());
        }).start();
    }

    private void print(String name) {
        synchronized (waitObject) {
            System.out.println(name);
        }
    }
}