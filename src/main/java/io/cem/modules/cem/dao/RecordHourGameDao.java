package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourGameEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordHourGameDao extends BaseDao<RecordHourGameEntity> {
    List<RecordHourGameEntity> queryGameList(Map<String, Object> map);
    List<RecordHourGameEntity> queryExitList(Map<String, Object> map);
    List<RecordHourGameEntity> queryGameAreaList(Map<String, Object> map);
    List<RecordHourGameEntity> queryGameRankList(Map<String, Object> map);
    List<RecordHourGameEntity> queryDayList(Map<String, Object> map);

    List<RecordHourGameEntity> queryIntervalList(Map<String, Object> map);

    int queryIntervalTotal(Map<String, Object> map);
}
