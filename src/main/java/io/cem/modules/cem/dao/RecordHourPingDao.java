package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourPingEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-02 14:35:31
 */
public interface RecordHourPingDao extends BaseDao<RecordHourPingEntity> {
    List<RecordHourPingEntity> queryPingList(Map<String, Object> map);
    List<RecordHourPingEntity> queryDayList(Map<String, Object> map);
}
