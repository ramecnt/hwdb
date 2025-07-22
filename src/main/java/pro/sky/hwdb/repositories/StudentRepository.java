package pro.sky.hwdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pro.sky.hwdb.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAge(int age);

    Collection<Student> findStudentsByAgeBetween(int minAge, int maxAge);

    @Query(value = "SELECT count(*) from student", nativeQuery = true)
    Integer studentAmount();

    @Query(value = "SELECT avg(age) from student", nativeQuery = true)
    Float avgAge();

    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    Collection<Student> lastStudents();
}
