package cloud.example.myprojectdiplom.services;

import cloud.example.myprojectdiplom.entity.StorageFile;
import cloud.example.myprojectdiplom.entity.User;
import cloud.example.myprojectdiplom.exception.DeleteFileException;
import cloud.example.myprojectdiplom.exception.InputDataException;
import cloud.example.myprojectdiplom.exception.UnauthorizedException;
import cloud.example.myprojectdiplom.models.request.FileDataApply;
import cloud.example.myprojectdiplom.models.response.FileJsonName;
import cloud.example.myprojectdiplom.repositories.AuthRepository;
import cloud.example.myprojectdiplom.repositories.FileRepository;
import cloud.example.myprojectdiplom.repositories.LoginRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class FileService {
    private FileRepository fileRepository;
    private AuthRepository authRepository;
    private LoginRepository loginRepository;

    // Загрузка файла
    public boolean upLoadFile(String token, String filename, MultipartFile file) {
        final User user = getAuthToken(token);
        if (user == null) {
            log.error("Upload file: Unautorized");
            throw new UnauthorizedException("Uoload file: Unautorized");
        }

        try {
            fileRepository.save(new StorageFile(filename, LocalDateTime.now(), file.getSize(), file.getBytes(), user));
            log.info("Success upload file. user {}", user.getUsername());
            return true;
        } catch (IOException e) {
            log.error("Upload file: Input data exception");
            throw new InputDataException("Upload file: Input data exception");
        }
    }

    // Удаление файла
    @Transactional
    public void deleteFile(String token, String filename) {
        final User user = getAuthToken(token);
        if (user == null) {
            log.error("Delete file : Unautorized");
            throw new UnauthorizedException("Delete file: Unautorized");
        }

        fileRepository.deleteByUserAndFilename(user, filename);

        final StorageFile storageFile = fileRepository.findByUserAndFilename(user, filename);
        if (storageFile != null) {
            log.error("Delete file: Input data exception");
            throw new InputDataException("Input data exception");
        }
        log.info("Success delete file. User {}", user.getUsername());
    }

    public byte[] downloadFile(String authToken, String filename) {
        User user = getAuthToken(authToken);
        userIsNull(user);

        final StorageFile storageFile = fileRepository.findByUserAndFilename(user, filename);
        if (storageFile == null) {
            log.error("Download file: Input data exception");
            throw new InputDataException("Download file: Input data exception");
        }
        log.info("Download file: User {}", user.getUsername());
        return storageFile.getFileContent();
    }

    @Transactional
    public void updateFilename(String authToken, String filename, FileDataApply fileDataApply) {
        final User user = getAuthToken(authToken);
        userIsNull(user);

        fileRepository.updateFilenameByUser(user, filename, fileDataApply.getFilename());

        storageFileisNull(user, filename);

        log.info("Success put file name. User {}", user.getUsername());
    }


    public List<FileJsonName> getAllFiles(String authToken, Integer limit) {
        final User user = getAuthToken(authToken);
        userIsNull(user);

        log.info("Success get all files. USer {}", user.getUsername());

        return fileRepository.findAllByUser(user).stream()
                .map(o -> new FileJsonName(o.getFilename(), o.getSize()))
                .collect(Collectors.toList());
    }

    private boolean storageFileisNull(User user, String filename) {
        StorageFile storageFile = fileRepository.findByUserAndFilename(user ,filename);
        if (storageFile != null) {
            log.error("ERROR: Input data exception");
            throw new InputDataException("ERROR: Input data exception");
        }
        return true;
    }
    private User getAuthToken(String token) {
        if (token.startsWith("Bearer ")) {
            final String authTokenBearer = token.split(" ")[1];
            final String username = authRepository.getUsernameByToken(authTokenBearer);
            return loginRepository.findByUsername(username);
        }
        return null;
    }
    private boolean userIsNull(User user) {
        if (user == null) {
            log.error("User is Unautorized");
            throw new UnauthorizedException("User is Unautorized");
        }
        return true;
        }
    }
