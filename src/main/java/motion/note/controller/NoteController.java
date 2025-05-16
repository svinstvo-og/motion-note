package motion.note.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("motion/api/v1/notes")
public class NoteController {


    public String ping() {
        return "pong";
    }

}
