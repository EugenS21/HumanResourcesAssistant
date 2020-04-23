package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.File.TABLE_NAME;

import java.util.Set;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = TABLE_NAME)
@Setter
@Getter
public class File {

    protected static final String TABLE_NAME = "t_file";

    @Id
    private Long id;

    private UUID uuid;

    private String fileName;

    private String fileType;

    @Lob
    private byte[] data;

    @ManyToMany (fetch = FetchType.LAZY)
    @JoinTable (name = "t_user_file",
                joinColumns = @JoinColumn (name = "user_id"),
                inverseJoinColumns = @JoinColumn (name = "file_id"))
    private Set<Employee> employees;

    public File(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public File() {
        this.uuid = UUID.randomUUID();
    }
}
