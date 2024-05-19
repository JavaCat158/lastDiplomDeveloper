package cloud.example.myprojectdiplom.repositories;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static cloud.example.myprojectdiplom.ConstantsTest.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

    @Autowired
    private LoginRepository loginRepository;

    @BeforeEach
    void setUp() {
        loginRepository.deleteAll();
        loginRepository.save(USER_1);
    }

    @Test
    void findByUsername() {
        assertEquals(USER_1, loginRepository.findByUsername(USERNAME_1));
    }

    @Test
    void notFindByUsername() {
        assertNull(loginRepository.findByUsername(USERNAME_2));
    }
}