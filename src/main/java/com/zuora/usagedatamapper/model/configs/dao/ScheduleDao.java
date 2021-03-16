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
@Table(name = "Schedule")
public class ScheduleDao extends AuditModel {

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

    @NotBlank(message = "cronString is required")
    private String cronString;

    /*
    @OneToOne
    @JoinColumn(name = "configId", referencedColumnName = "id")
    @JsonIgnore
    @NotBlank(message = "Instance Config Id is required")
    private InstanceConfigDao instanceConfigDao;

    public ScheduleDao(String cronString, Long configId) {
        this.cronString = cronString;
        InstanceConfigDao instanceConfigDao = new InstanceConfigDao();
        instanceConfigDao.setId(configId);
        this.instanceConfigDao = instanceConfigDao;
    }
*/

}
