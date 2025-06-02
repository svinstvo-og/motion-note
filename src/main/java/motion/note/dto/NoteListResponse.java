package motion.note.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import motion.note.model.Link;
import motion.note.model.Note;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Getter
@Setter
public class NoteListResponse {
    @JsonProperty("note-id")
    private UUID noteId;
    @JsonProperty("user-id")
    private UUID userId;
    private String name;
    @JsonProperty("created-at")
    private Timestamp createdAt;
    @JsonProperty("updated-at")
    private Timestamp updatedAt;
    @JsonProperty("s3-key")
    private String s3key;

    @Nullable
    private List<LinkResponse> links;

    public NoteListResponse(Note note) {
        this.noteId = note.getNoteId();
        this.userId = note.getUserId();
        this.name = note.getName();
        this.createdAt = note.getCreatedAt();
        this.updatedAt = note.getUpdatedAt();
        this.s3key = note.getS3key();
        this.links = new ArrayList<>();

        List<Link> tempLinks = note.getLinks();

        if (tempLinks != null) {
            for (Link link : tempLinks) {
                this.links.add(new LinkResponse(link));
            }
        }
    }
}
