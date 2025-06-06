package motion.note.controller;

import lombok.extern.slf4j.Slf4j;
import motion.note.dto.NoteListResponse;
import motion.note.dto.NoteSaveRequest;
import motion.note.service.NoteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("motion/api/v1/notes")
@Slf4j
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/foo")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFoo() {
        noteService.addFoo();
    }

    @GetMapping("/foo")
    public List<NoteListResponse> getFoo() {
        List<NoteListResponse> notes = noteService.getAllNotes();
        log.info("Found {} notes", notes.size());
        return notes;
    }

    @PostMapping("/new/{name}")
    public void createNote(@PathVariable String name, @RequestHeader("X-User-Context") String userIdString) {
        UUID userId = UUID.fromString(userIdString);
        noteService.createNote(name, userId);
    }

    @PatchMapping("/{noteId}/save")
    public void saveNote(@PathVariable String noteId, @RequestHeader("X-User-Context") String userIdString,
                         @RequestBody NoteSaveRequest request) {
        if (request.getTitle() != null) {
            //TODO updating the title
        }
        if (request.getContent() != null) {
            noteService.saveContent(request.getContent(), userIdString, noteId, false);
        }
    }

    @GetMapping("/{noteId}/content")
    public String getNoteContent(@PathVariable String noteId, @RequestHeader("X-User-Context") String userIdString) {
        return noteService.getNoteContent(noteId, userIdString, false);
    }
}
