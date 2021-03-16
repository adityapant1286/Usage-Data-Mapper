package com.zuora.usagedatamapper.services;

import com.zuora.usagedatamapper.errorhandling.exceptions.IORealmRuntimeException;
import com.zuora.usagedatamapper.model.configs.dao.IORealmDao;
import com.zuora.usagedatamapper.model.configs.vo.IORealm;
import com.zuora.usagedatamapper.repositories.configs.IORealmRepository;
import com.zuora.usagedatamapper.transformers.Transformer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.zuora.usagedatamapper.util.AppUtil.commonExclusionFields;
import static com.zuora.usagedatamapper.util.AppUtil.formatStr;
import static com.zuora.usagedatamapper.util.Constants.FAILED_TO_DELETE;
import static com.zuora.usagedatamapper.util.Constants.FAILED_TO_SAVE;
import static com.zuora.usagedatamapper.util.Constants.RECORD_NOT_FOUND_BY;

@Service(value = "defaultIORealmService")
public class IORealmServiceImpl implements IORealmService {

    private final IORealmRepository IORealmRepository;

    public IORealmServiceImpl(IORealmRepository IORealmRepository) {
        this.IORealmRepository = IORealmRepository;
    }

    @Override
    public IORealm findIORealmById(UUID id) {
        final IORealmDao dao = IORealmRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                        "SftpConnection", "id=" + id)));

        return toVo().transform(dao);
    }

    @Override
    public IORealm findIORealmByInstanceConfigId(UUID instanceConfigId) {
        final IORealmDao dao = IORealmRepository.findIORealmDaoByInstanceConfigId(instanceConfigId)
                                    .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                                            "SftpConnection", "configId=" + instanceConfigId)));

        return toVo().transform(dao);
    }

    @Override
    public List<IORealm> findIORealmDaoByNameOrHost(String name, String host) {
        final List<IORealmDao> daos = IORealmRepository.findIORealmDaosByNameOrHost(name, host)
                                    .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                                            "SftpConnection", "Name=" + name + " or Host=" + host)));

        return toVos().transform(daos);
    }

    @Override
    public IORealm createIORealm(IORealm IORealm) {
        final IORealmDao dao = toDao().transform(IORealm);

        IORealmDao saved = Optional.of(IORealmRepository.save(dao))
                .orElseThrow(() -> new IORealmRuntimeException(formatStr(FAILED_TO_SAVE, "Schedule")));

        return toVo().transform(saved);
    }

    @Override
    public IORealm updateIORealmById(UUID id, IORealm IORealm) {
        final IORealmDao dao = toDao().transform(IORealm);
        dao.setId(id);

        IORealmDao saved = Optional.of(IORealmRepository.save(dao))
                .orElseThrow(() -> new IORealmRuntimeException(formatStr(FAILED_TO_SAVE, "Schedule")));

        return toVo().transform(saved);
    }

    @Override
    public IORealm updateIORealmByInstanceConfigId(UUID instanceConfigId, IORealm IORealm) {
        final IORealmDao found = IORealmRepository.findIORealmDaoByInstanceConfigId(instanceConfigId)
                                        .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                                                "SftpConnection", "configId=" + instanceConfigId)));

        Transformer.transform(IORealm, found, commonExclusionFields());

        IORealmDao saved = Optional.of(IORealmRepository.save(found))
                .orElseThrow(() -> new IORealmRuntimeException(formatStr(FAILED_TO_SAVE, "Schedule")));

        return toVo().transform(saved);
    }

    @Override
    public IORealm updateIORealmByName(String name, IORealm IORealm) {
        final IORealmDao found = IORealmRepository.findIORealmDaoByName(name)
                .orElseThrow(() -> new IllegalArgumentException(formatStr(RECORD_NOT_FOUND_BY,
                        "SftpConnection", "Name=" + name)));

        Transformer.transform(IORealm, found, commonExclusionFields());
        return null;
    }

    @Override
    public void deleteById(UUID id) {
        try {
            IORealmRepository.deleteById(id);
        } catch (Exception ex) {
            throw new IORealmRuntimeException(formatStr(FAILED_TO_DELETE,"SftpConnection", "id=" + id));
        }
    }

    @Override
    public void deleteByName(String name) {
        try {
            IORealmRepository.deleteByName(name);
        } catch (Exception ex) {
            throw new IORealmRuntimeException(formatStr(FAILED_TO_DELETE,"SftpConnection", "name=" + name));
        }

    }

    @Override
    public void deleteByInstanceConfigId(UUID instanceConfigId) {
        try {
            IORealmRepository.deleteByInstanceConfigId(instanceConfigId);
        } catch (Exception ex) {
            throw new IORealmRuntimeException(formatStr(FAILED_TO_DELETE,"SftpConnection",
                    "instanceConfigId=" + instanceConfigId));
        }
    }

    private static Transformer<IORealm, IORealmDao> toDao() {
        return (vo) -> {
            IORealmDao dao = new IORealmDao();
            BeanUtils.copyProperties(vo, dao, IORealmDao.class);
            return dao;
        };
    }

    private static Transformer<IORealmDao, IORealm> toVo() {
        return (dao) -> {
            IORealm vo = new IORealm();
            BeanUtils.copyProperties(dao, vo, IORealm.class);
            return vo;
        };
    }
    private static Transformer<List<IORealmDao>, List<IORealm>> toVos() {
        return (daos) -> daos.stream()
                            .map(dao -> toVo().transform(dao))
                            .collect(Collectors.toList());
    }
}
