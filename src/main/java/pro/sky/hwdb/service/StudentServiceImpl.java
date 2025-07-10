package pro.sky.hwdb.service;

import pro.sky.hwdb.model.Faculty;
import pro.sky.hwdb.model.Student;

import java.util.Collection;

public interface StudentServiceImpl {
    Student addStudent(Student student);

    Student findStudent(long id);

    Student editStudent(Student student);

    void deleteStudent(long id);

    Collection<Student> findByAge(int age);

    Collection<Student> findByAgeBetween(int min, int max);

    Faculty getFaculty(long id);

    Integer studentAmount();

    Float avgAge();

    Collection<Student> lastStudents();
}
