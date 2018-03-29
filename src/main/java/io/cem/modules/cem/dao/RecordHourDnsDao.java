package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourDnsEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordHourDnsDao extends BaseDao<RecordHourDnsEntity> {
    List<RecordHourDnsEntity> queryDnsList(Map<String, Object> map);
    List<RecordHourDnsEntity> queryExitList(Map<String, Object> map);
    List<RecordHourDnsEntity> queryDayList(Map<String, Object> map);

    List<RecordHourDnsEntity> queryIntervalList(Map<String, Object> map);

    int queryIntervalTotal(Map<String, Object> map);}
