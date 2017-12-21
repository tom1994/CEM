package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourDnsEntity;
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
public interface RecordHourDnsDao extends BaseDao<RecordHourDnsEntity> {
    List<RecordHourDnsEntity> queryDnsList(Map<String, Object> map);
    List<RecordHourDnsEntity> queryDayList(Map<String, Object> map);
}
