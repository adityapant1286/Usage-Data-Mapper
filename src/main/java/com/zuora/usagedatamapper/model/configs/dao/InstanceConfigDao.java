package com.zuora.usagedatamapper.model.configs.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "InstanceConfig")
public class InstanceConfigDao extends AuditModel {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", nullable = false, columnDefinition = "text")
    private UUID id;

    @NotBlank(message = "tenantId is required")
    private String tenantId; // required
    private String entityId; // conditional required

    @NotBlank(message = "name is required")
    @Size(min = 3, message = "Minimum 3 characters required")
    private String name; // required
/*
    private String filePath;
    private String inclusion;
    private String exclusion;
*/

    public InstanceConfigDao(UUID id) {
        this.id = id;
    }

    public InstanceConfigDao(String name) {
        this.name = name;
    }
}
