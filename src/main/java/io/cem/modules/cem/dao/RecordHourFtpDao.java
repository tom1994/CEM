package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourFtpEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordHourFtpDao extends BaseDao<RecordHourFtpEntity> {
    List<RecordHourFtpEntity> queryFtpList(Map<String, Object> map);
    List<RecordHourFtpEntity> queryExitList(Map<String, Object> map);
    List<RecordHourFtpEntity> queryDayExitList(Map<String, Object> map);
    List<RecordHourFtpEntity> queryDayList(Map<String, Object> map);

    List<RecordHourFtpEntity> queryIntervalList(Map<String, Object> map);

    int queryIntervalTotal(Map<String, Object> map);
}
