package cloud.example.myprojectdiplom.controllers;

import cloud.example.myprojectdiplom.entity.StorageFile;
import cloud.example.myprojectdiplom.entity.User;
import cloud.example.myprojectdiplom.models.request.FileDataApply;
import cloud.example.myprojectdiplom.models.request.LoginAuth;
import cloud.example.myprojectdiplom.models.response.FileJsonName;
import cloud.example.myprojectdiplom.models.response.GetToken;
import cloud.example.myprojectdiplom.services.AuthService;
import cloud.example.myprojectdiplom.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class CloudControllers {
    private final AuthService authService;
    private final FileService fileService;

    @Autowired
    CloudControllers(AuthService authService, FileService fileService) {
        this.authService = authService;
        this.fileService = fileService;
    }

    @PostMapping(value = "/login")
    public GetToken login(@RequestBody LoginAuth login) {
        return authService.login(login);
    }

    @PostMapping(value = "/file")
    public ResponseEntity<?> uploadFile(
            @RequestHeader("auth-token") String authToken,
            @RequestParam("filename") String filename,
            @RequestParam("file") MultipartFile file) {
        User user = authService.getUserFromToken(authToken);
        fileService.uploadFile(user, filename, file);
        return ResponseEntity.ok("File uploaded successfully");
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename) {
        User user = authService.getUserFromToken(authToken);
        fileService.deleteFile(user, filename);
        return ResponseEntity.ok("File deleted successfully");
    }

    @GetMapping("/file")
    public ResponseEntity<byte[]> downloadFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename) {
        User user = authService.getUserFromToken(authToken);
        byte[] file = fileService.downloadFile(user, filename);
        return ResponseEntity.ok(file);
    }

    @PutMapping("/file")
    public ResponseEntity<?> updateFilename(@RequestHeader("auth-token") String authToken,
                                            @RequestParam("filename") String filename,
                                            @RequestBody FileDataApply fileDataApply) {
        User user = authService.getUserFromToken(authToken);
        fileService.updateFilename(user, filename, fileDataApply);
        return ResponseEntity.ok("File name updated successfully");
    }

    @GetMapping("/list")
    public ResponseEntity<List<FileJsonName>> getAllFiles(@RequestHeader("auth-token") String authToken, @RequestParam("limit") Integer limit) {
        User user = authService.getUserFromToken(authToken);
        List<StorageFile> storageFiles = fileService.getAllFiles(user, limit);
        List<FileJsonName> fileJsonNames = storageFiles.stream()
                .map(storageFile -> new FileJsonName(storageFile.getFilename(), storageFile.getSize()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(fileJsonNames);
    }
}
