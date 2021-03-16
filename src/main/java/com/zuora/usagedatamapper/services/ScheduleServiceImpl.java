package com.zuora.usagedatamapper.services;

import com.zuora.usagedatamapper.errorhandling.exceptions.ScheduleRuntimeException;
import com.zuora.usagedatamapper.model.configs.dao.ScheduleDao;
import com.zuora.usagedatamapper.model.configs.vo.Schedule;
import com.zuora.usagedatamapper.repositories.configs.ScheduleRepository;
import com.zuora.usagedatamapper.transformers.Transformer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Optional;
import java.util.UUID;

import static com.zuora.usagedatamapper.util.AppUtil.commonExclusionFields;
import static com.zuora.usagedatamapper.util.AppUtil.formatStr;
import static com.zuora.usagedatamapper.util.Constants.FAILED_TO_DELETE;
import static com.zuora.usagedatamapper.util.Constants.FAILED_TO_SAVE;
import static com.zuora.usagedatamapper.util.Constants.RECORD_NOT_FOUND_BY;

@Service(value = "defaultScheduleService")
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    public Schedule findById(UUID id) {

        final ScheduleDao dao = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                        "Schedule", "id=" + id)));

        return toVo().transform(dao);
    }

    @Override
    public Schedule findByInstanceConfigId(UUID instanceConfigId) {
        ScheduleDao dao = scheduleRepository.findByInstanceConfigId(instanceConfigId)
                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                        "Schedule", "configId=" + instanceConfigId)));

        return toVo().transform(dao);
    }

    @Override
    public Schedule createSchedule(Schedule schedule) {

        final ScheduleDao dao = toDao().transform(schedule);
        ScheduleDao saved = Optional.of(scheduleRepository.save(dao))
                .orElseThrow(() -> new ScheduleRuntimeException(formatStr(FAILED_TO_SAVE, "Schedule")));

        return toVo().transform(saved);
    }

    @Override
    public Schedule updateScheduleById(UUID id, Schedule schedule) {
        final ScheduleDao dao = toDao().transform(schedule);
        dao.setId(id);

        final ScheduleDao saved = Optional.of(scheduleRepository.save(dao))
                .orElseThrow(() -> new ScheduleRuntimeException(formatStr(FAILED_TO_SAVE, "Schedule")));

        return toVo().transform(saved);
    }

    @Override
    public Schedule updateScheduleByInstanceConfigId(UUID instanceConfigId, Schedule schedule) {
        final ScheduleDao found = scheduleRepository.findByInstanceConfigId(instanceConfigId)
                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                        "InstanceConfig", "id=" + instanceConfigId)));

        Transformer.transform(schedule, found, commonExclusionFields());

        ScheduleDao saved = Optional.of(scheduleRepository.save(found))
                .orElseThrow(() -> new ScheduleRuntimeException(formatStr(FAILED_TO_SAVE, "Schedule")));

        return toVo().transform(saved);
    }

    @Override
    public void deleteById(UUID id) {
        try {
            scheduleRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ScheduleRuntimeException(formatStr(FAILED_TO_DELETE,"Schedule", "id=" + id));
        }
    }

    @Override
    public void deleteByInstanceConfigId(UUID instanceConfigId) {
        try {
            scheduleRepository.deleteByInstanceConfigId(instanceConfigId);
        } catch (Exception ex) {
            throw new ScheduleRuntimeException(formatStr(FAILED_TO_DELETE,"Schedule",
                    "instanceConfigId=" + instanceConfigId));
        }
    }

    private static Transformer<Schedule, ScheduleDao> toDao() {
        return (vo) -> {
            ScheduleDao dao = new ScheduleDao();
            BeanUtils.copyProperties(vo, dao, ScheduleDao.class);
            return dao;
        };
    }

    private static Transformer<ScheduleDao, Schedule> toVo() {
        return (dao) -> {
            Schedule vo = new Schedule();
            BeanUtils.copyProperties(dao, vo, Schedule.class);
            return vo;
        };
    }
}
