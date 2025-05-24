package motion.note.repository;

import motion.note.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserReadRepository extends JpaRepository<User, UUID> {
}
