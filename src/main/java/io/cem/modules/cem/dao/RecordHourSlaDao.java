package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourSlaEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;


/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-12 15:11:35
 */
public interface RecordHourSlaDao extends BaseDao<RecordHourSlaEntity> {
    List<RecordHourSlaEntity> querySlaList(Map<String, Object> map);
    List<RecordHourSlaEntity> queryDayList(Map<String, Object> map);
}
