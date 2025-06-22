package pro.sky.hwdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.hwdb.model.Student;

import java.util.Collection;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findStudentsByAge(int age);
}
