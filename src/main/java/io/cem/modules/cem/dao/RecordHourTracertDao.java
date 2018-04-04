package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordHourTracertDao extends BaseDao<RecordHourTracertEntity> {
    List<RecordHourTracertEntity> queryTracertList(Map<String, Object> map);
    List<RecordHourTracertEntity> queryDayList(Map<String, Object> map);
    List<RecordHourTracertEntity> queryExitList(Map<String, Object> map);
    List<RecordHourTracertEntity> queryDayExitList(Map<String, Object> map);
    List<RecordHourTracertEntity> queryIntervalList(Map<String,Object> map);
    int queryIntervalTotal(Map<String,Object> map);
}
