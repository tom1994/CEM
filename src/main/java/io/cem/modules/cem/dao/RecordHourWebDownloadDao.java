package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourWebDownloadEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordHourWebDownloadDao extends BaseDao<RecordHourWebDownloadEntity> {
    List<RecordHourWebDownloadEntity> queryWebDownloadList(Map<String, Object> map);
    List<RecordHourWebDownloadEntity> queryExitList(Map<String, Object> map);
    List<RecordHourWebDownloadEntity> queryDayExitList(Map<String, Object> map);
    List<RecordHourWebDownloadEntity> queryDayList(Map<String, Object> map);
    List<RecordHourWebDownloadEntity> queryIntervalList(Map<String,Object> map);
    int queryIntervalTotal(Map<String,Object> map);
}
