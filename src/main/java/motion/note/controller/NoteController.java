package motion.note.controller;

import motion.note.model.Note;
import motion.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("motion/api/v1/notes")
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

    @GetMapping("foo")
    public List<Note> getFoo() {
        return noteService.getAllNotes();
    }

    @PostMapping("/new/{name}")
    public void createNote(@PathVariable String name, @RequestHeader("X-User-Context") String userIdString) {
        UUID userId = UUID.fromString(userIdString);
        noteService.createNote(name, userId);
    }
}
