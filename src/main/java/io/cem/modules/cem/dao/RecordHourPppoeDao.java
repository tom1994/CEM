package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourPppoeEntity;

import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;
/**
 */
public interface RecordHourPppoeDao extends BaseDao<RecordHourPppoeEntity> {
    List<RecordHourPppoeEntity> queryPppoeList(Map<String, Object> map);
    List<RecordHourPppoeEntity> queryExitList(Map<String, Object> map);
    List<RecordHourPppoeEntity> queryDayList(Map<String, Object> map);
    List<RecordHourPppoeEntity> queryIntervalList(Map<String,Object> map);
    int queryIntervalTotal(Map<String,Object> map);
}
