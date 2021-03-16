package com.zuora.usagedatamapper.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuora.usagedatamapper.model.configs.dao.ConfigurationWrapper;
import com.zuora.usagedatamapper.model.configs.dao.InstanceConfigDao;
import com.zuora.usagedatamapper.model.configs.dao.MappingDao;
import com.zuora.usagedatamapper.model.configs.dao.MappingsDao;
import com.zuora.usagedatamapper.model.configs.dao.ScheduleDao;
import org.junit.Test;

import java.util.List;

public class ConfigurationWrapperTest {

    @Test
    public void testJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();

/*
        MappingsDao mappingsDao = MappingsDao.builder()
                .totalSize(1)
                .currentSegmentSize(1)
                .totalPages(1)
                .data(List.of(new MappingDao("from", "String",
                        "to", "Account", 1L)))
                .build();
        ConfigurationWrapper wrapper = ConfigurationWrapper.builder()
                .instanceConfigDao(new InstanceConfigDao("name1"))
                .scheduleDao(new ScheduleDao("1 1 * *", 1L))
                .mappingsDao(mappingsDao)
                .build();
*/

//        wrapper.setSftpConnection(new SftpConnection("sftp1",
//                "localhost", 22,
//                "/",
//                "user1", "xyz", 1L));
//        wrapper.setMappings(List.of(new Mapping("from", "String",
//                "to", "Account", 1L)));

//        String json = mapper.writerWithDefaultPrettyPrinter()
//                            .writeValueAsString(wrapper);
//        System.out.println(json);
    }
}