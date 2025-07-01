package pro.sky.hwdb.service;

import org.springframework.stereotype.Service;
import pro.sky.hwdb.model.Faculty;
import pro.sky.hwdb.model.Student;
import pro.sky.hwdb.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Set;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty editFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public Collection<Faculty> findByColor(String color) {
        return facultyRepository.findFacultiesByColor(color);
    }

    public Collection<Faculty> findByString(String search) {
        return facultyRepository.findFacultiesByNameContainingIgnoreCaseOrColorContainingIgnoreCase(search, search);
    }

    public Set<Student> getStudents(long id) {
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow();
        return faculty.getStudents();
    }
}