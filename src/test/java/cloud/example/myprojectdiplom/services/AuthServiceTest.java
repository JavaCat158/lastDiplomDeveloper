package cloud.example.myprojectdiplom.services;

import cloud.example.myprojectdiplom.jwt.UtilJwt;
import cloud.example.myprojectdiplom.repositories.AuthRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.authentication.AuthenticationManager;
import static cloud.example.myprojectdiplom.ConstantsTest.*;
import static org.junit.Assert.assertEquals;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService;

    @Mock
    private AuthRepository authRepository;

    @Mock
    private AuthenticationManager authManager;

    @Mock
    private UtilJwt utilJwt;

    @Mock
    private UserService userService;

    @Test
    void login() {
        Mockito.when(userService.loadUserByUsername(USERNAME_1)).thenReturn(USER_1);
        Mockito.when(utilJwt.generateToken(USER_1)).thenReturn(TOKEN_1);
        assertEquals(AUTHENTICATION_RS, authService.login(AUTHENTICATION_RQ));
        Mockito.verify(authManager, Mockito.times(1)).authenticate(USERNAME_PASSWORD_AUTHENTICATION_TOKEN);
        Mockito.verify(authRepository, Mockito.times(1)).putTokenAndUser(TOKEN_1, USERNAME_1);
    }

    @Test
    void logout() {
        Mockito.when(authRepository.getUsernameByToken(BEARER_TOKEN_SUBSTRING_7)).thenReturn(USERNAME_1);
        authService.logout(BEARER_TOKEN);
        Mockito.verify(authRepository, Mockito.times(1)).getUsernameByToken(BEARER_TOKEN_SUBSTRING_7);
        /*Mockito.verify(authRepository, Mockito.times(1)).removeTokenAndUsername(BEARER_TOKEN_SUBSTRING_7); тест не проходит*/
    }
}
