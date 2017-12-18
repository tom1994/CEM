package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourTracertEntity;
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
public interface RecordHourTracertDao extends BaseDao<RecordHourTracertEntity> {
    List<RecordHourTracertEntity> queryTracertList(Map<String, Object> map);
    List<RecordHourTracertEntity> queryDayList(Map<String, Object> map);
}
