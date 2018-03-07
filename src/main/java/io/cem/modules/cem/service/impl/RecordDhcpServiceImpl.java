package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.RecordHourDhcpDao;
import io.cem.modules.cem.entity.RecordHourDhcpEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import io.cem.modules.cem.dao.RecordDhcpDao;
import io.cem.modules.cem.entity.RecordDhcpEntity;
import io.cem.modules.cem.service.RecordDhcpService;


@Service("recordDhcpService")
public class RecordDhcpServiceImpl implements RecordDhcpService {
    @Autowired
    private RecordDhcpDao recordDhcpDao;

    @Autowired
    private RecordHourDhcpDao recordHourDhcpDao;

    @Override
    public RecordDhcpEntity queryObject(Integer id) {
        return recordDhcpDao.queryObject(id);
    }

    @Override
    public List<RecordDhcpEntity> queryList(Map<String, Object> map) {
        return recordDhcpDao.queryList(map);
    }

    @Override
    public List<RecordDhcpEntity> queryDhcpList(Map<String, Object> map) {
        return recordDhcpDao.queryDhcpList(map);
    }

    @Override
    public List<RecordHourDhcpEntity> queryIntervalList(Map<String, Object> map) {
        return recordHourDhcpDao.queryIntervalList(map);
    }

    @Override
    public int queryIntervalTotal(Map<String, Object> map) {
        return recordHourDhcpDao.queryIntervalTotal(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map) {
        return recordDhcpDao.queryTotal(map);
    }

    @Override
    public List<RecordDhcpEntity> queryDhcpTest(Map<String, Object> map) {
        return recordDhcpDao.queryDhcpTest(map);
    }

    @Override
    public void save(RecordDhcpEntity recordDhcp) {
        recordDhcpDao.save(recordDhcp);
    }

    @Override
    public void update(RecordDhcpEntity recordDhcp) {
        recordDhcpDao.update(recordDhcp);
    }

    @Override
    public void delete(Integer id) {
        recordDhcpDao.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids) {
        recordDhcpDao.deleteBatch(ids);
    }

}
