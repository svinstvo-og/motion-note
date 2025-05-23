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
import java.util.Optional;
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

    @Transactional
    public void createNote(String name, UUID userId) {
        Note note = new Note();
        note.setName(name);
        note.setUserId(userId);
        note.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.info("Saving note {}", note.getName());
        noteWriteRepository.save(note);
    }

    public void saveContent(String content, String userId, String noteId, boolean authorized) {
        Optional<Note> noteOptional = noteReadRepository.findById(UUID.fromString(noteId));
        if (noteOptional.isPresent()) {
            Note note = noteOptional.get();
            if (note.getUserId().toString().equals(userId) || authorized) {
                String key = "users/" + userId + "/notes/" + noteId;
                log.info("Saving content of note {} with key {}", note.getName(), key);
                try {
                    s3StorageService.uploadFile(key, content.getBytes());
                }
                catch (Exception e) {
                    log.error("Uploading content failed: {}", e.getMessage());
                    throw new RuntimeException(e);
                }
                note.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
                note.setS3key(key);
                noteWriteRepository.save(note);
            }
            else {
                log.error("User with id {} does not have permission to write to note: {}", userId, note.getName());
                throw new RuntimeException("User does not have permission to write note");
            }
        }
        else {
            log.error("Note with id {} does not exist", noteId);
            throw new RuntimeException("Note with id " + noteId + " does not exist");
        }
        log.info("Note {} saved", noteOptional.get().getName());
    }
}
