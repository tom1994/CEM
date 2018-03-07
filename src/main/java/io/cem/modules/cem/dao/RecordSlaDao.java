package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordDaySlaEntity;
import io.cem.modules.cem.entity.RecordHourSlaEntity;
import io.cem.modules.cem.entity.RecordSlaEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordSlaDao extends BaseDao<RecordSlaEntity> {
    List<RecordSlaEntity> querySlaTest(Map<String, Object> map);
    List<RecordHourSlaEntity> querySla(Map<String, Object> map);
    List<RecordDaySlaEntity> queryDay(Map<String, Object> map);
    List<RecordSlaEntity> querySlaList(Map<String, Object> map);
}
