package motion.note.dto;

import lombok.*;
import org.springframework.http.converter.json.GsonBuilderUtils;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NoteSaveRequest {
    private String title;
    private String content;
}
