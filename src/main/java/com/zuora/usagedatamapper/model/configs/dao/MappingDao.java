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
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "Mapping")
public class MappingDao extends AuditModel {

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

    @NotBlank(message = "fromFieldName is required")
    private String fromFieldName;
    @NotBlank(message = "fromFieldDataType is required")
    private String fromFieldDataType;
    @NotBlank(message = "usageFieldName is required")
    private String usageFieldName;
    @NotBlank(message = "objectName is required")
    private String objectName;

/*
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "configId", referencedColumnName = "id", nullable = false)
    @JsonIgnore
    private InstanceConfigDao instanceConfigDao;

    public MappingDao(String fromFieldName, String fromFieldDataType, String usageFieldName, String objectName,
                      Long configId) {
        this.fromFieldName = fromFieldName;
        this.fromFieldDataType = fromFieldDataType;
        this.usageFieldName = usageFieldName;
        this.objectName = objectName;
        InstanceConfigDao instanceConfigDao = new InstanceConfigDao();
        instanceConfigDao.setId(configId);
        this.instanceConfigDao = instanceConfigDao;
    }
*/
}
