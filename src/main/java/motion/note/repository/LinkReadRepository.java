package motion.note.repository;

import motion.note.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkReadRepository extends JpaRepository<Link, String> {
}
