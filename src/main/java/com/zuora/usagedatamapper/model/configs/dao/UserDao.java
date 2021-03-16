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
@Table(name = "users")
public class UserDao {

    @Id
    @NotBlank(message = "username is required")
    @Column(name = "username", nullable = false, columnDefinition = "text")
    private String username; // required

    @NotBlank(message = "password is required")
    private String password; // required

    @NotBlank(message = "enabled is required")
    private boolean enabled;

}
