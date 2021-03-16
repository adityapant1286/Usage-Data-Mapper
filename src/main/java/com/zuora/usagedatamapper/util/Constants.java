package com.zuora.usagedatamapper.util;

import java.text.SimpleDateFormat;

public final class Constants {
    private Constants() {}

    public static final String API_CURRENT_VERSION = "/v1";
    public static final String API_PROD_VERSION = "/v1";

    public static final String API_INSTANCE_CONFIGS = "/InstanceConfigs/**";
    public static final String API_SCHEDULES = "/Schedules/**";
    public static final String API_SFTP_CONNECTIONS = "/SftpConnections/**";
    public static final String API_MAPPINGS = "/Mappings/**";

    public static final String CONFIG_ID_REQUIRED = "InstanceConfig id is required";

    public static final String FAILED_TO_SAVE = "Failed to save %s";
    public static final String FAILED_TO_DELETE = "Failed to delete %s by %s";

    public static final String RECORD_NOT_FOUND_BY = "%s not found by %s.";

    public static final String EITHER_REQUIRED = "Either one is required. Missing %s.";
    public static final String ENTITY_ID_REQUIRED = "If you are using multi-entity tenant, " +
                                                    "then please provide the valid entity id";

    public static final String PATTERN_NUMERIC = "[\\d]+";
    public static final String PATTERN_TIMESTAMP = "yyyy-MM-ddTHH:mm:ssz";
    public static final SimpleDateFormat TO_TIMESTAMP_FORMAT = new SimpleDateFormat(PATTERN_TIMESTAMP);

}
