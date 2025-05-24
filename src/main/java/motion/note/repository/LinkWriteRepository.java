package motion.note.repository;

import motion.note.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkWriteRepository extends JpaRepository<Link, String> {
}
