package com.humanresources.assistant.backend.entity;

import static com.humanresources.assistant.backend.entity.FileEntity.TABLE_NAME;

import com.humanresources.assistant.backend.entity.authentication.UserEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name = TABLE_NAME)
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FileEntity {

    protected static final String TABLE_NAME = "t_file";

    @Id
    @Column
    @SequenceGenerator (name = "file_id_generator", sequenceName = "seq_file", allocationSize = 1)
    @GeneratedValue (strategy = GenerationType.SEQUENCE, generator = "file_id_generator")
    private Long id;

    @Column
    @NotNull
    private String fileName;

    @Column
    @NotNull
    private String fileType;

    @Column
    @NotNull
    private String phoneNumber;

    @Column
    @NotNull
    private String department;

    @Column
    private String description;

    @OneToOne
    @JoinTable (name = "t_user_files",
                joinColumns = @JoinColumn (name = "user_id", referencedColumnName = "id"),
                inverseJoinColumns = @JoinColumn (name = "file_id", referencedColumnName = "id"))
    private UserEntity user;
}
