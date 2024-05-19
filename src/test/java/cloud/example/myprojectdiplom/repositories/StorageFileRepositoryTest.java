package cloud.example.myprojectdiplom.repositories;

/*
import cloud.example.myprojectdiplom.entity.StorageFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static cloud.example.myprojectdiplom.ConstantsTest.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StorageFileRepositoryTest {

    @Autowired
    private FileRepository storageFileRepository;

    @Autowired
    private LoginRepository loginRepository;

    @BeforeEach
    void setUp() {
        loginRepository.deleteAll();
        storageFileRepository.deleteAll();
        loginRepository.save(USER_1);
        storageFileRepository.save(STORAGE_FILE_1);
        storageFileRepository.save(FOR_RENAME_STORAGE_FILE);
    }

    @Test
    void deleteByUserAndFilename() {
        Optional<StorageFile> beforeDelete = storageFileRepository.findById(FILENAME_1);
        assertTrue(beforeDelete.isPresent());
        storageFileRepository.deleteByUserAndFilename(USER_1, FILENAME_1);
        Optional<StorageFile> afterDelete = storageFileRepository.findById(FILENAME_1);
        assertFalse(afterDelete.isPresent());
    }

    @Test
    void findByUserAndFilename() {
        assertEquals(STORAGE_FILE_1, storageFileRepository.findByUserAndFilename(USER_1, FILENAME_1));
    }

    @Test
    void editFileNameByUser() {
        Optional<StorageFile> beforeEditName = storageFileRepository.findById(FOR_RENAME_FILENAME);
        assertTrue(beforeEditName.isPresent());
        assertEquals(FOR_RENAME_FILENAME, beforeEditName.get().getFilename());
        storageFileRepository.updateFilenameByUser(USER_1, FOR_RENAME_FILENAME, NEW_FILENAME);
        Optional<StorageFile> afterEditName = storageFileRepository.findById(NEW_FILENAME);
        assertTrue(afterEditName.isPresent());
        assertEquals(NEW_FILENAME, afterEditName.get().getFilename());
    }

    @Test
    void findAllByUser() {
        assertEquals(List.of(STORAGE_FILE_1, FOR_RENAME_STORAGE_FILE), storageFileRepository.findAllByUser(USER_1));
    }
}*/
