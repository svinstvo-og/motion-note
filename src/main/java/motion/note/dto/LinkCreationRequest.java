package motion.note.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkCreationRequest {
    @JsonProperty("valid-until")
    @Nullable
    private Timestamp validUntil;
    @JsonProperty("note-id")
    private UUID noteId;
}
