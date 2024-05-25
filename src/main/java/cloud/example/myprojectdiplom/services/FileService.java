package cloud.example.myprojectdiplom.services;

import cloud.example.myprojectdiplom.entity.StorageFile;
import cloud.example.myprojectdiplom.entity.User;
import cloud.example.myprojectdiplom.exception.InputDataException;
import cloud.example.myprojectdiplom.models.request.FileDataApply;
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

@Service
@AllArgsConstructor
@Slf4j
public class FileService {
    private final FileRepository fileRepository;
    private final AuthRepository authRepository;
    private final LoginRepository loginRepository;

    public void uploadFile(User user, String filename, MultipartFile file) {
        try {
            fileRepository.save(new StorageFile(filename, LocalDateTime.now(), file.getSize(), file.getBytes(), user));
            log.info("Successfully uploaded file. User: {}", user.getUsername());
        } catch (IOException e) {
            log.error("Upload file: Input data exception");
            throw new InputDataException("Upload file: Input data exception");
        }
    }

    @Transactional
    public void deleteFile(User user, String filename) {
        fileRepository.deleteByUserAndFilename(user, filename);
        StorageFile storageFile = fileRepository.findByUserAndFilename(user, filename);
        if (storageFile != null) {
            log.error("Delete file: Input data exception");
            throw new InputDataException("Input data exception");
        }
        log.info("Successfully deleted file. User: {}", user.getUsername());
    }

    public byte[] downloadFile(User user, String filename) {
        StorageFile storageFile = fileRepository.findByUserAndFilename(user, filename);
        if (storageFile == null) {
            log.error("Download file: Input data exception");
            throw new InputDataException("Download file: Input data exception");
        }
        log.info("Downloaded file. User: {}", user.getUsername());
        return storageFile.getFileContent();
    }

    @Transactional
    public void updateFilename(User user, String filename, FileDataApply fileDataApply) {
        fileRepository.updateFilenameByUser(user, filename, fileDataApply.getFilename());
        StorageFile storageFile = fileRepository.findByUserAndFilename(user, filename);
        if (storageFile != null) {
            log.error("ERROR: Input data exception");
            throw new InputDataException("ERROR: Input data exception");
        }
        log.info("Successfully updated file name. User: {}", user.getUsername());
    }

    public List<StorageFile> getAllFiles(User user, Integer limit) {
        log.info("Successfully fetched all files. User: {}", user.getUsername());
        return fileRepository.findAllByUser(user);
    }
}