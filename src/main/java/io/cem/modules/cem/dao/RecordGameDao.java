package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordDayGameEntity;
import io.cem.modules.cem.entity.RecordGameEntity;
import io.cem.modules.cem.entity.RecordHourGameEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface RecordGameDao extends BaseDao<RecordGameEntity> {
    List<RecordGameEntity> queryGameTest(Map<String, Object> map);
    List<RecordHourGameEntity> queryGame(Map<String, Object> map);
    List<RecordDayGameEntity> queryDay(Map<String, Object> map);
    List<RecordGameEntity> queryGameList(Map<String, Object> map);
}
