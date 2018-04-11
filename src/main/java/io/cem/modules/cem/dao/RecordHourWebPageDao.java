package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourWebPageEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;
/**
 */
public interface RecordHourWebPageDao extends BaseDao<RecordHourWebPageEntity> {
    List<RecordHourWebPageEntity> queryWebList(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryExitList(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryDayExitList(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryWebAreaList(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryWebRankList(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryDayList(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryDayRankList(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryDayAreaList(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryDayTargetList(Map<String, Object> map);
    List<RecordHourWebPageEntity> queryIntervalList(Map<String,Object> map);
    int queryIntervalTotal(Map<String,Object> map);
}
