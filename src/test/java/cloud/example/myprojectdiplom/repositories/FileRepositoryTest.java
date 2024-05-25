package cloud.example.myprojectdiplom.repositories;


import cloud.example.myprojectdiplom.entity.StorageFile;
import cloud.example.myprojectdiplom.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.*;


@Testcontainers
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class FileRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest")
            .withPassword("password")
            .withUsername("username")
            .withDatabaseName("test");

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private LoginRepository loginRepository;

    private User user;
    private StorageFile storageFile;

    @DynamicPropertySource
    public static void property(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        dynamicPropertyRegistry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        dynamicPropertyRegistry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @BeforeEach
    void setup() {
        user = new User();
        user.setUsername("postgres");
        user.setPassword("password");
        loginRepository.save(user);

        storageFile = new StorageFile();
        storageFile.setUser(user);
        storageFile.setDate(LocalDateTime.now());
        storageFile.setSize(10L);
        storageFile.setFileContent("test.txt".getBytes());
        storageFile.setFilename("test.txt");
        fileRepository.save(storageFile);
    }

    @Test
    @Transactional
    void deleteByUserAndFilenameTest() {
        fileRepository.deleteByUserAndFilename(user, "test.txt");

        StorageFile deleteFile = fileRepository.findByUserAndFilename(user, "test.txt");

        assertNull(deleteFile);
    }

    @Test
    @Transactional
    void testFindByUserAndFileTest() {
        StorageFile found = fileRepository.findByUserAndFilename(user, "test.txt");
        assertNotNull(found);
        assertEquals(storageFile.getFilename(), found.getFilename());
    }

    @Test
    @Transactional
    void updateFilenameByUserTest() {
        fileRepository.updateFilenameByUser(user, "test.txt", "any.txt");
        StorageFile update = fileRepository.findByUserAndFilename(user, "any.txt");
        assertNotNull((update));
        assertEquals("any.txt", update.getFilename());
    }

    @Test
    @Transactional
    void findAllUserTest() {
        List<StorageFile> storageFiles = fileRepository.findAll();

        assertNotNull(storageFiles);
        assertEquals(storageFiles.get(0).getFilename(), storageFile.getFilename());
        assertFalse(storageFiles.isEmpty());
        assertEquals(1, storageFiles.size());
    }
}
