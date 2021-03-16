package com.zuora.usagedatamapper.model.configs.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "IORealm")
public class IORealmDao extends AuditModel {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, columnDefinition = "text")
    private UUID id;

    @NotBlank(message = "instanceConfigId is required")
    @Column(nullable = false, columnDefinition = "text")
    private UUID instanceConfigId;
    @NotBlank(message = "category is required")
    @Column(nullable = false)
    private String category;

    @NotBlank(message = "name is required")
    @Size(min = 3, message = "Minimum 3 characters required")
    private String name;
    @NotBlank(message = "host is required")
    private String host;
    @NotBlank(message = "port is required")
    private Integer port;
    @NotBlank(message = "username is required")
    private String username;
    private String secret;
    private String sshKey;
    private String sourcePath;
    private String inclusion;
    private String exclusion;

/*
    @OneToOne
    @JoinColumn(name = "configId", referencedColumnName = "id")
    @JsonIgnore
    private InstanceConfigDao instanceConfigDao;

//    @ConstructorProperties({"name", "host", "port", "sourcePath", "username", "secret", "configId"})
    public SftpConnectionDao(String name,
                             String host, Integer port, String sourcePath,
                             String username, String secret, Long configId) {
        this.name = name;
        this.host = host;
        this.port = port;
        this.username = username;
        this.secret = secret;
        this.sourcePath = sourcePath;
        InstanceConfigDao instanceConfigDao = new InstanceConfigDao();
        instanceConfigDao.setId(configId);
        this.instanceConfigDao = instanceConfigDao;
    }
*/

}
