package com.zuora.usagedatamapper.model.configs.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@ApiModel(description = "Sftp Connection details for an instance")
public class IORealm {

    @ApiModelProperty(notes = "A unique identifier of an Sftp Connection. " +
                            "A value for this field is required for PUT/update operation",
                        position = 1,
                        allowEmptyValue = true)
    private UUID id;

    @ApiModelProperty(notes = "A unique instanceConfig identifier for which this Sftp Connection is related to",
                        position = 2,
                        allowEmptyValue = true)
    private UUID configId;

    @ApiModelProperty(notes = "A unique name of an Sftp Connection. Minimum 3 characters required.",
                        position = 3,
                        required = true)
    @NotBlank(message = "name is required")
    @Size(min = 3, message = "Minimum 3 characters required")
    private String name;

    @ApiModelProperty(notes = "A valid Sftp Host name or IP address.",
                        position = 4,
                        required = true)
    @NotBlank(message = "host is required")
    private String host;

    @ApiModelProperty(notes = "A valid Sftp port number of the host.",
                        position = 5,
                        required = true)
    @NotBlank(message = "port is required")
    private Integer port;

    @ApiModelProperty(notes = "A valid Sftp username of the host.",
                        position = 6,
                        required = true)
    @NotBlank(message = "username is required")
    private String username;

    @ApiModelProperty(notes = "A valid Sftp password of the host. This field is required if the SFTP connection is compatible with Username/Password",
                        position = 7,
                        allowEmptyValue = true)
    private String secret;

    @ApiModelProperty(notes = "A valid Sftp SSH key of the host. This field is required if the SFTP connection is compatible with SSH",
                        position = 8,
                        allowEmptyValue = true)
    private String sshKey;

    @ApiModelProperty(notes = "A valid file or folder location path of localhost or SFTP Host machine. ",
                        position = 9,
                        required = true)
    private String sourcePath;

    @ApiModelProperty(notes = "A regular expression or a comma separated list of file extensions to be considered, in case " +
            "the value for filePath is a folder location",
            position = 10,
            allowEmptyValue = true)
    private String inclusion;

    @ApiModelProperty(notes = "A regular expression or a comma separated list of file extensions to be excluded, in case " +
            "the value for filePath is a folder location",
            position = 11,
            allowEmptyValue = true)
    private String exclusion;

}
