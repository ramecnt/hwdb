package pro.sky.hwdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pro.sky.hwdb.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {

    Optional<Avatar> findByStudentId(Long studentId);

}
