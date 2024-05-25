package cloud.example.myprojectdiplom.services;

import cloud.example.myprojectdiplom.entity.User;
import cloud.example.myprojectdiplom.jwt.UtilJwt;
import cloud.example.myprojectdiplom.models.response.GetToken;
import cloud.example.myprojectdiplom.models.request.LoginAuth;
import cloud.example.myprojectdiplom.repositories.AuthRepository;
import cloud.example.myprojectdiplom.repositories.LoginRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {
    private AuthRepository authRepository;
    private AuthenticationManager authManager;
    private UtilJwt utilJwt;
    private UserService userService;
    private final LoginRepository loginRepository;

    public GetToken login(LoginAuth loginAuth) {
        final String username = loginAuth.getLogin();
        final String password = loginAuth.getPassword();

        authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        final UserDetails userDetails = userService.loadUserByUsername(username);
        final String token = utilJwt.generateToken(userDetails);

        authRepository.putTokenAndUser(token, username);
        log.info("User {} authenticate. JWT: {}", username, token);
        return new GetToken(token);
    }


    public void logout(String token) {
        final String usertoken = token.substring(7);
        final String username = authRepository.getUsernameByToken(usertoken);
        log.info("User {} logout, JWT is disabled", username);
        authRepository.removeTokenAndUsername(token);
    }

    public User getUserFromToken(String token) {
        if (token.startsWith("Bearer ")) {
            String authTokenBearer = token.split(" ")[1];
            String username = authRepository.getUsernameByToken(authTokenBearer);
            return loginRepository.findByUsername(username);
        }
        return null;
    }
}
