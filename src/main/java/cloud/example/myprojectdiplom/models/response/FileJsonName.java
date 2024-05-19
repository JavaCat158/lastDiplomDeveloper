package cloud.example.myprojectdiplom.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileJsonName {
    private String filename;
    private Long size;
}
