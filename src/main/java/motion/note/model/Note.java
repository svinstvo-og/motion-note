package motion.note.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "notes")
public class Note {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
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
}
