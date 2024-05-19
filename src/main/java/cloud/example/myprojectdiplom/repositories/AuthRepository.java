package cloud.example.myprojectdiplom.repositories;

import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class AuthRepository {

    private final Map<String, String> tokenAndUsers = new ConcurrentHashMap<>();

    public void putTokenAndUser(String token, String username) {
        tokenAndUsers.put(token, username);
    }

    public void removeTokenAndUsername(String token) {
        tokenAndUsers.remove(token);
    }

    public String getUsernameByToken(String token) {
        return tokenAndUsers.get(token);
    }
}
