package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordHourPingDao extends BaseDao<RecordHourPingEntity> {
    List<RecordHourPingEntity> queryPingList(Map<String, Object> map);
    List<RecordHourPingEntity> queryTargetHourList(Map<String, Object> map);
    List<RecordHourPingEntity> queryDayList(Map<String, Object> map);
    List<RecordHourPingEntity> queryExitList(Map<String, Object> map);
    List<RecordHourPingEntity> queryDayExitList(Map<String, Object> map);
    List<RecordHourPingEntity> queryIntervalList(Map<String,Object> map);
    int queryIntervalTotal(Map<String,Object> map);
}
