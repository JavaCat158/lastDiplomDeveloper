package cloud.example.myprojectdiplom.services;

import cloud.example.myprojectdiplom.exception.InputDataException;
import cloud.example.myprojectdiplom.exception.UnauthorizedException;
import cloud.example.myprojectdiplom.repositories.AuthRepository;
import cloud.example.myprojectdiplom.repositories.FileRepository;
import cloud.example.myprojectdiplom.repositories.LoginRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static cloud.example.myprojectdiplom.ConstantsTest.*;
import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class FileServiceTest {
    @InjectMocks
    private FileService fileService;
    @Mock
    private FileRepository fileRepository;
    @Mock
    private AuthRepository authRepository;
    @Mock
    private LoginRepository loginRepository;

    @BeforeEach
    void set() {
        Mockito.when(authRepository.getUsernameByToken(BEARER_TOKEN_SPLIT)).thenReturn(USERNAME_1);
        Mockito.when(loginRepository.findByUsername(USERNAME_1)).thenReturn(USER_1);
    }

    @Test
    void upload() {
        assertTrue(fileService.upLoadFile(BEARER_TOKEN, FILENAME_1, MULTIPART_FILE));
    }

    @Test
    void delete() {
        fileService.deleteFile(BEARER_TOKEN, FILENAME_1);
        Mockito.verify(fileRepository, Mockito.times(1)).deleteByUserAndFilename(USER_1, FILENAME_1);
    }

    @Test
    void deleteFileUnauthorized() {
        Assertions.assertThrows(UnauthorizedException.class, () -> fileService.deleteFile(TOKEN_1, FILENAME_1));
    }

    @Test
    void deleteFileInputException() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(InputDataException.class, () -> fileService.deleteFile(BEARER_TOKEN, FILENAME_1));
    }

    @Test
    void download() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertEquals(FILE_CONTENT_1, fileService.downloadFile(BEARER_TOKEN, FILENAME_1));
    }

    @Test
    void downloadUnautorized() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(UnauthorizedException.class, () -> fileService.downloadFile(TOKEN_1, FILENAME_1));
    }

    @Test
    void downloadInputException() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(InputDataException.class, () -> fileService.downloadFile(BEARER_TOKEN, FILENAME_2));
    }

    @Test
    void update() {
        fileService.updateFilename(BEARER_TOKEN, FILENAME_1, EDIT_FILE_NAME_RQ);
        Mockito.verify(fileRepository, Mockito.times(1)).updateFilenameByUser(USER_1, FILENAME_1, NEW_FILENAME);
    }

    @Test
    void updateUnautprizedException() {
        assertThrows(UnauthorizedException.class, () -> fileService.updateFilename(TOKEN_1, FILENAME_1, EDIT_FILE_NAME_RQ));
    }

    @Test
    void updateInputDataException() {
        Mockito.when(fileRepository.findByUserAndFilename(USER_1, FILENAME_1)).thenReturn(STORAGE_FILE_1);
        assertThrows(InputDataException.class, () -> fileService.updateFilename(BEARER_TOKEN, FILENAME_1, EDIT_FILE_NAME_RQ));
    }

    @Test
    void getAallFiles() {
        Mockito.when(fileRepository.findAllByUser(USER_1)).thenReturn(STORAGE_FILE_LIST);
        assertEquals(FILE_RS_LIST, fileService.getAllFiles(BEARER_TOKEN, LIMIT));
    }

    @Test
    void allFilesUnautorized() {
        Mockito.when(fileRepository.findAllByUser(USER_1)).thenReturn(STORAGE_FILE_LIST);
        assertThrows(UnauthorizedException.class, () -> fileService.getAllFiles(TOKEN_1, LIMIT));
    }
}
