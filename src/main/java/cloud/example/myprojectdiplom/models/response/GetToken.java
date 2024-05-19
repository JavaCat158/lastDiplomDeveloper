package cloud.example.myprojectdiplom.models.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class GetToken {

    @JsonProperty("auth-token")
    String autToken;
}
