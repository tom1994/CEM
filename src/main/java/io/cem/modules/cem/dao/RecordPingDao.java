package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordDayPingEntity;
import io.cem.modules.cem.entity.RecordFailEntity;
import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.cem.entity.RecordPingEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordPingDao extends BaseDao<RecordPingEntity> {
    List<RecordPingEntity> queryPingTest(Map<String, Object> map);
    List<RecordHourPingEntity> queryPing(Map<String, Object> map);
    List<RecordDayPingEntity> queryDay(Map<String, Object> map);
    List<RecordFailEntity> queryPingFail(Map<String, Object> map);
}
