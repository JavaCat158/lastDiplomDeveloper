package cloud.example.myprojectdiplom.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthRepositoryTest {

    private AuthRepository authRepository;
    private final Map<String, String> testTokenMapUsers = new ConcurrentHashMap<>();

    @BeforeEach
    void setUp() {
        authRepository = new AuthRepository();
        authRepository.putTokenAndUser("token1", "username1");
        testTokenMapUsers.clear();
        testTokenMapUsers.put("token1", "username1");
    }

    @Test
    void tokenAndUsername() {
        String before = authRepository.getUsernameByToken("token1");
        assertNotNull(before);
        authRepository.putTokenAndUser("token2", "username2");
        String after = authRepository.getUsernameByToken("token2");
        assertEquals("username2", after);
    }

    @Test
    void removeToken() {
        String before = authRepository.getUsernameByToken("token1");
        assertNotNull(before);
        authRepository.removeTokenAndUsername("token1");
        String after = authRepository.getUsernameByToken("token1");
        assertNull(after);
    }

    @Test
    void usernameGetTokenTest() {
        assertEquals(testTokenMapUsers.get("token1"), authRepository.getUsernameByToken("token1"));
    }
}
