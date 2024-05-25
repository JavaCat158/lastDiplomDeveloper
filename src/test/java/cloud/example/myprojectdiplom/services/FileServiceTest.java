package cloud.example.myprojectdiplom.services;

import cloud.example.myprojectdiplom.entity.StorageFile;
import cloud.example.myprojectdiplom.entity.User;
import cloud.example.myprojectdiplom.exception.InputDataException;
import cloud.example.myprojectdiplom.repositories.FileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@SpringBootTest
public class FileServiceTest {

    @Mock
    private FileRepository fileRepository;

    @InjectMocks
    private FileService fileService;

    @Test
    void uploadFile_Success() throws IOException {
        User user = new User("username", "password");
        MockMultipartFile file = new MockMultipartFile("testFile", "testData".getBytes());

        fileService.uploadFile(user, "filename", file);

        verify(fileRepository, times(1)).save(any(StorageFile.class));
    }

    @Test
    void uploadFile_InputDataException() {
        User user = new User("username", "password");
        MockMultipartFile file = new MockMultipartFile("testFile", "testData".getBytes());
        doThrow(new InputDataException("Upload file: Input data exception")).when(fileRepository).save(any(StorageFile.class));

        assertThrows(InputDataException.class, () -> fileService.uploadFile(user, "filename", file));
    }

    @Test
    void deleteFile_Success() {
        User user = new User("username", "password");

        assertDoesNotThrow(() -> fileService.deleteFile(user, "filename"));

        verify(fileRepository, times(1)).deleteByUserAndFilename(user, "filename");
    }

    @Test
    void deleteFile_InputDataException() {
        User user = new User("username", "password");
        doReturn(new StorageFile("filename", LocalDateTime.now(), 100L, new byte[0], user)).when(fileRepository).findByUserAndFilename(user, "filename");

        assertThrows(InputDataException.class, () -> fileService.deleteFile(user, "filename"));
    }

    @Test
    void downloadFile_Success() {
        User user = new User("username", "password");
        byte[] fileContent = "testData".getBytes();
        StorageFile storageFile = new StorageFile("filename", LocalDateTime.now(), 100L, fileContent, user);
        doReturn(storageFile).when(fileRepository).findByUserAndFilename(user, "filename");

        byte[] downloadedContent = fileService.downloadFile(user, "filename");

        assertArrayEquals(fileContent, downloadedContent);
    }
}