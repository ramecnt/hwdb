package pro.sky.hwdb.service;

import pro.sky.hwdb.model.Faculty;
import pro.sky.hwdb.model.Student;

import java.util.Collection;
import java.util.Set;

public interface FacultyServiceImpl   {
    Faculty addFaculty(Faculty faculty);

    Faculty findFaculty(long id);

    Faculty editFaculty(Faculty faculty);

    void deleteFaculty(long id);

    Collection<Faculty> findByColor(String color);

    Collection<Faculty> findByString(String search);

    Set<Student> getStudents(long id);

    String longestName();

    long sum();
}
