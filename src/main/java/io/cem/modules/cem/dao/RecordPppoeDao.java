package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordDayPppoeEntity;
import io.cem.modules.cem.entity.RecordHourPppoeEntity;
import io.cem.modules.cem.entity.RecordPppoeEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordPppoeDao extends BaseDao<RecordPppoeEntity> {
    List<RecordPppoeEntity> queryPppoeTest(Map<String, Object> map);
    List<RecordHourPppoeEntity> queryPppoe(Map<String, Object> map);
    List<RecordDayPppoeEntity> queryDay(Map<String, Object> map);
    List<RecordPppoeEntity> queryPppoeList(Map<String, Object> map);
}
