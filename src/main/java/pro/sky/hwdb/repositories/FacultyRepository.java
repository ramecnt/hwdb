package pro.sky.hwdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.hwdb.model.Faculty;

import java.util.Collection;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
    Collection<Faculty> findAllByColor(String color);
}
