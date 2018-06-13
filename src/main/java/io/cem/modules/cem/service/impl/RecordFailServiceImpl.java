package io.cem.modules.cem.service.impl;

import io.cem.modules.cem.dao.*;
import io.cem.modules.cem.entity.RecordFailEntity;
import io.cem.modules.cem.service.RecordFailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("recordFailService")
public class RecordFailServiceImpl implements RecordFailService {
    @Autowired
    private RecordFailDao recordFailDao;
    @Autowired
    private RecordPingDao recordPingDao;
    @Autowired
    private RecordTracertDao recordTracertDao;
    @Autowired
    private RecordSlaDao recordSlaDao;
    @Autowired
    private RecordDhcpDao recordDhcpDao;
    @Autowired
    private RecordDnsDao recordDnsDao;
    @Autowired
    private RecordPppoeDao recordPppoeDao;
    @Autowired
    private RecordRadiusDao recordRadiusDao;
    @Autowired
    private RecordWebPageDao recordWebPageDao;
    @Autowired
    private RecordWebDownloadDao recordWebDownloadDao;
    @Autowired
    private RecordFtpDao recordFtpDao;
    @Autowired
    private RecordWebVideoDao recordWebVideoDao;
    @Autowired
    private RecordGameDao recordGameDao;

    @Override
    public RecordFailEntity queryObject(Integer id){
        return recordFailDao.queryObject(id);
    }

    @Override
    public List<RecordFailEntity> queryList(Map<String, Object> map){
        return recordFailDao.queryList(map);
    }

    @Override
    public List<RecordFailEntity> queryPingFail(Map<String, Object> map){
        return recordPingDao.queryPingFail(map);
    }

    @Override
    public List<RecordFailEntity> queryTracertFail(Map<String, Object> map){
        return recordTracertDao.queryTracertFail(map);
    }
    @Override
    public List<RecordFailEntity> querySlaFail(Map<String, Object> map){
        return recordSlaDao.querySlaFail(map);
    }

    @Override
    public List<RecordFailEntity> queryDhcpFail(Map<String, Object> map){
        return recordDhcpDao.queryDhcpFail(map);
    }

    @Override
    public List<RecordFailEntity> queryDnsFail(Map<String, Object> map){
        return recordDnsDao.queryDnsFail(map);
    }

    @Override
    public List<RecordFailEntity> queryPppoeFail(Map<String, Object> map){
        return recordPppoeDao.queryPppoeFail(map);
    }
    @Override
    public List<RecordFailEntity> queryRadiusFail(Map<String, Object> map){
        return recordRadiusDao.queryRadiusFail(map);
    }

    @Override
    public List<RecordFailEntity> queryWebDownloadFail(Map<String, Object> map){
        return recordWebDownloadDao.queryWebDownloadFail(map);
    }

    @Override
    public List<RecordFailEntity> queryFtpFail(Map<String, Object> map){
        return recordFtpDao.queryFtpFail(map);
    }

    @Override
    public List<RecordFailEntity> queryVideoFail(Map<String, Object> map){
        return recordWebVideoDao.queryVideoFail(map);
    }

    @Override
    public List<RecordFailEntity> queryGameFail(Map<String, Object> map){
        return recordGameDao.queryGameFail(map);
    }


    @Override
    public List<RecordFailEntity> queryWebPageFail(Map<String, Object> map){
        return recordWebPageDao.queryWebPageFail(map);
    }


    @Override
    public List<RecordFailEntity> queryFail(Map<String, Object> map){
        return recordFailDao.queryFail(map);
    }

    @Override
    public int queryTotal(Map<String, Object> map){
        return recordFailDao.queryTotal(map);
    }

    @Override
    public void save(RecordFailEntity recordFail){
        recordFailDao.save(recordFail);
    }

    @Override
    public void update(RecordFailEntity recordFail){
        recordFailDao.update(recordFail);
    }

    @Override
    public void delete(Integer id){
        recordFailDao.delete(id);
    }

    @Override
    public void deleteBatch(Integer[] ids){
        recordFailDao.deleteBatch(ids);
    }
}
