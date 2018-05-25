package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordDayTracertEntity;
import io.cem.modules.cem.entity.RecordFailEntity;
import io.cem.modules.cem.entity.RecordHourTracertEntity;
import io.cem.modules.cem.entity.RecordTracertEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordTracertDao extends BaseDao<RecordTracertEntity> {
    List<RecordTracertEntity> queryTracertTest(Map<String, Object> map);
    List<RecordHourTracertEntity> queryTracert(Map<String,Object> map);
    List<RecordDayTracertEntity> queryDay(Map<String,Object> map);
    List<RecordTracertEntity> queryTracertList(Map<String, Object> map);
    List<RecordFailEntity> queryTracertFail(Map<String, Object> map);
}
