package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourDhcpEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordHourDhcpDao extends BaseDao<RecordHourDhcpEntity> {
    List<RecordHourDhcpEntity> queryDhcpList(Map<String, Object> map);
    List<RecordHourDhcpEntity> queryTargetHourList(Map<String, Object> map);
    List<RecordHourDhcpEntity> queryExitList(Map<String, Object> map);

    List<RecordHourDhcpEntity> queryDayExitList(Map<String, Object> map);

    List<RecordHourDhcpEntity> queryDayList(Map<String, Object> map);

    List<RecordHourDhcpEntity> queryIntervalList(Map<String, Object> map);

    int queryIntervalTotal(Map<String, Object> map);
        }
