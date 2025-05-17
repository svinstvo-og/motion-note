package motion.note.service;

import motion.note.model.Note;
//import motion.note.repository.NoteReadRepository;
//import motion.note.repository.NoteWriteRepository;
import motion.note.repository.NoteReadRepository;
import motion.note.repository.NoteWriteRepository;
import org.springframework.stereotype.Service;

@Service
public class NoteService {

    NoteReadRepository noteReadRepository;
    NoteWriteRepository noteWriteRepository;

    public NoteService(NoteReadRepository noteReadRepository, NoteWriteRepository noteWriteRepository) {
        this.noteReadRepository = noteReadRepository;
        this.noteWriteRepository = noteWriteRepository;
    }

    public void addFoo() {
        Note foo = new Note();
        noteWriteRepository.save(foo);
    }

}
