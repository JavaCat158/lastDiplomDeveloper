package cloud.example.myprojectdiplom.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


@NoArgsConstructor
@Data
@Entity
@Table(name = "files")
public class StorageFile {

    @Id
    @Column(nullable = false, unique = true)
    private String filename;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false)
    private long size;

    @Lob
    @Column(nullable = false)
    private byte[] fileContent;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public StorageFile(User user, String test) {
        this.user = user;
    }

    public StorageFile(String filename, LocalDateTime date, long size, byte[] fileContent, User user) {
        this.filename = filename;
        this.date = date;
        this.size = size;
        this.fileContent = fileContent;
        this.user = user;
    }
}
