package com.humanresources.assistant.backend.model;

import static com.humanresources.assistant.backend.model.File.TABLE_NAME;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
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

    public File(String fileName, String fileType, byte[] data) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
    }

    public File() {
        this.uuid = UUID.randomUUID();
    }
}
