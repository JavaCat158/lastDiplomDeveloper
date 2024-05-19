package cloud.example.myprojectdiplom.models.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@Data
public class LoginAuth {


    @NotBlank(message = "login must not be null")
    private String login;


    @NotBlank(message = "password must not be null")
    private String password;
}
