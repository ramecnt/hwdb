package pro.sky.hwdb.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.hwdb.model.Faculty;
import pro.sky.hwdb.model.Student;
import pro.sky.hwdb.repositories.FacultyRepository;

import java.util.Collection;
import java.util.Set;

@Service
public class FacultyService implements FacultyServiceImpl {
    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    @Override
    public Faculty addFaculty(Faculty faculty) {
        logger.info("add faculty {}", faculty);
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty findFaculty(long id) {
        logger.info("find faculty with id {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    @Override
    public Faculty editFaculty(Faculty faculty) {
        logger.info("edit faculty {}", faculty);
        return facultyRepository.save(faculty);
    }

    @Override
    public void deleteFaculty(long id) {
        logger.info("delete faculty with id {}", id);
        facultyRepository.deleteById(id);
    }

    @Override
    public Collection<Faculty> findByColor(String color) {
        logger.info("find faculties with color {}", color);
        return facultyRepository.findFacultiesByColor(color);
    }

    @Override
    public Collection<Faculty> findByString(String search) {
        logger.info("find faculties with search {}", search);
        return facultyRepository.findFacultiesByNameContainingIgnoreCaseOrColorContainingIgnoreCase(search, search);
    }

    @Override
    public Set<Student> getStudents(long id) {
        logger.info("get students with faculty id {}", id);
        Faculty faculty = facultyRepository.findById(id)
                .orElseThrow();
        return faculty.getStudents();
    }
}