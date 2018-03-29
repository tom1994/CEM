package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourSlaEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;


/**
 */
public interface RecordHourSlaDao extends BaseDao<RecordHourSlaEntity> {
    List<RecordHourSlaEntity> querySlaList(Map<String, Object> map);
    List<RecordHourSlaEntity> queryExitList(Map<String, Object> map);
    List<RecordHourSlaEntity> queryDayList(Map<String, Object> map);
    List<RecordHourSlaEntity> queryIntervalList(Map<String,Object> map);
    int queryIntervalTotal(Map<String,Object> map);
}
