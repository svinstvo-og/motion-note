package motion.note.service;

import jakarta.transaction.Transactional;
import motion.note.model.Note;
import motion.note.repository.NoteReadRepository;
import motion.note.repository.NoteWriteRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
public class NoteService {

    NoteReadRepository noteReadRepository;
    NoteWriteRepository noteWriteRepository;

    public NoteService(NoteReadRepository noteReadRepository, NoteWriteRepository noteWriteRepository) {
        this.noteReadRepository = noteReadRepository;
        this.noteWriteRepository = noteWriteRepository;
    }

    @Transactional
    public void addFoo() {
        Note foo = new Note();
        foo.setName("foo");
        foo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        noteWriteRepository.save(foo);
    }

}
