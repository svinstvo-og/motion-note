package motion.note.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import motion.note.model.Link;
import motion.note.model.Note;
import motion.note.model.User;

import java.sql.Timestamp;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkResponse {
    private String token;
    private Timestamp validUntil;
    private Timestamp createdAt;

    private Map<UUID, String> note;
    @Nullable
    private List<String> users;

    LinkResponse(Link link) {
        this.token = link.getToken();
        this.validUntil = link.getValidUntil();
        this.createdAt = link.getCreatedAt();

        this.note = new HashMap<>();
        note.put(link.getNote().getNoteId(), link.getNote().getName());

        List<String> users = new ArrayList<>();
        this.users = users;
        List<User> tempUsers = link.getUsers();
        for (User user : tempUsers) {
            users.add(user.getUserId().toString());
        }
    }
}
