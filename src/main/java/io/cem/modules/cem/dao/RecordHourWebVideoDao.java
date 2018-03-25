package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourWebVideoEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordHourWebVideoDao extends BaseDao<RecordHourWebVideoEntity> {
    List<RecordHourWebVideoEntity> queryVideoList(Map<String, Object> map);
    List<RecordHourWebVideoEntity> queryVideoAreaList(Map<String, Object> map);
    List<RecordHourWebVideoEntity> queryVideoRankList(Map<String, Object> map);
    List<RecordHourWebVideoEntity> queryDayList(Map<String, Object> map);
    List<RecordHourWebVideoEntity> queryIntervalList(Map<String,Object> map);
    int queryIntervalTotal(Map<String,Object> map);
}
