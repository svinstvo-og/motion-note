package motion.note.repository;

import motion.note.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NoteWriteRepository extends JpaRepository<Note, UUID> {
}
