package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourRadiusEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;
/**
 */
public interface RecordHourRadiusDao extends BaseDao<RecordHourRadiusEntity> {
    List<RecordHourRadiusEntity> queryRadiusList(Map<String, Object> map);
    List<RecordHourRadiusEntity> queryExitList(Map<String, Object> map);
    List<RecordHourRadiusEntity> queryDayList(Map<String, Object> map);
    List<RecordHourRadiusEntity> queryIntervalList(Map<String,Object> map);
    int queryIntervalTotal(Map<String,Object> map);
}
