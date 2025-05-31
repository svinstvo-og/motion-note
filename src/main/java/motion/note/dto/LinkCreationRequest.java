package motion.note.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.sql.Timestamp;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LinkCreationRequest {
    @JsonProperty("valid-until")
    private Timestamp validUntil;


}
