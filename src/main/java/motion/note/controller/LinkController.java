package motion.note.controller;


import lombok.extern.slf4j.Slf4j;
import motion.note.dto.LinkCreationRequest;
import motion.note.model.Note;
import motion.note.service.HashService;
import motion.note.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("motion/api/v1/notes/link")
@Slf4j
public class LinkController {

    private final NoteService noteService;
    private final HashService hashService;

    public LinkController(NoteService noteService, HashService hashService) {
        this.noteService = noteService;
        this.hashService = hashService;
    }

    @PostMapping("/create")
    public void createLink(@RequestBody LinkCreationRequest request,
                           @RequestHeader("X-User-Context") String userIdString) throws InterruptedException {
        log.info("Accepted create link request. UserId: {}, noteId: {}", userIdString, request.getNoteId());
        Note note = noteService.validateNote(request.getNoteId(), userIdString);
        hashService.createLink(request.getValidUntil(), note);
    }
}
