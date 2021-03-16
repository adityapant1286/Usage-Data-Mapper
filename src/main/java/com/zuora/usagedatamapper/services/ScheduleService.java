package com.zuora.usagedatamapper.services;

import com.zuora.usagedatamapper.model.configs.vo.Schedule;

import java.util.UUID;

public interface ScheduleService {
    Schedule findById(UUID id);
    Schedule findByInstanceConfigId(UUID instanceConfigId);

    Schedule createSchedule(Schedule schedule);
    Schedule updateScheduleById(UUID id, Schedule schedule);
    Schedule updateScheduleByInstanceConfigId(UUID instanceConfigId, Schedule schedule);

    void deleteById(UUID id);
    void deleteByInstanceConfigId(UUID instanceConfigId);
}
