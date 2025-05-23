package motion.note.service;


import lombok.extern.slf4j.Slf4j;
import motion.note.model.Note;
import motion.note.repository.NoteReadRepository;
import motion.note.repository.NoteWriteRepository;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class NoteService {

    NoteReadRepository noteReadRepository;
    NoteWriteRepository noteWriteRepository;
    S3StorageService s3StorageService;

    public NoteService(NoteReadRepository noteReadRepository, NoteWriteRepository noteWriteRepository, S3StorageService s3StorageService) {
        this.noteReadRepository = noteReadRepository;
        this.noteWriteRepository = noteWriteRepository;
        this.s3StorageService = s3StorageService;
    }

    @Transactional
    public void addFoo() {
        Note foo = new Note();
        foo.setName("foo");
        foo.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.info("Saving foo");
        try {noteWriteRepository.save(foo);}
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Note> getAllNotes() {
        log.info("Getting all notes");
        List<Note> notes = noteReadRepository.findAll();
        log.info("Returning all notes");
        //System.out.println(notes.toString());
        return notes;
    }

    public void createNote(String name, UUID userId) {
        Note note = new Note();
        note.setName(name);
        note.setUserId(userId);
        note.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
    }


    public void saveContent(String content, String userId, String noteId) {
        String key = "users/" + userId + "/notes/" + noteId;
        log.info("Saving content with key {}", key);
        try {
            s3StorageService.uploadFile(key, content.getBytes());
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
