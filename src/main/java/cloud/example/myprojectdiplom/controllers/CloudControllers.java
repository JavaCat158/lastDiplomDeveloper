package cloud.example.myprojectdiplom.controllers;

import cloud.example.myprojectdiplom.models.request.FileDataApply;
import cloud.example.myprojectdiplom.models.request.LoginAuth;
import cloud.example.myprojectdiplom.models.response.FileJsonName;
import cloud.example.myprojectdiplom.models.response.GetToken;
import cloud.example.myprojectdiplom.services.AuthService;
import cloud.example.myprojectdiplom.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class CloudControllers {
    private final AuthService authService;
    private final FileService fileService;

    @Autowired
    CloudControllers(AuthService authService, FileService fileService) {
        this.fileService = fileService;
        this.authService = authService;
    }

    @PostMapping(value = "/login")
    public GetToken login(@RequestBody LoginAuth login) {
        return authService.login(login);
    }

    @PostMapping(value = "/file")
    public ResponseEntity<?> uploadFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename, MultipartFile file) {
        fileService.upLoadFile(authToken, filename, file);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/file")
    public ResponseEntity<?> deleteFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename) {
        fileService.deleteFile(authToken, filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/file")
    public ResponseEntity<?> downLoadFile(@RequestHeader("auth-token") String authToken, @RequestParam("filename") String filename) {
        byte[] file = fileService.downloadFile(authToken, filename);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PutMapping("/file")
    public ResponseEntity<?> putFile(@RequestHeader("auth-token") String authToken,
                                     @RequestParam("filename") String filename,
                                     @RequestBody FileDataApply fileDataApply) {
        fileService.updateFilename(authToken, filename, fileDataApply);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/list")
    public List<FileJsonName> getAllFiles(@RequestHeader("auth-token") String authToken, @RequestParam("limit") Integer limit) {
        return fileService.getAllFiles(authToken, limit);
    }
}
