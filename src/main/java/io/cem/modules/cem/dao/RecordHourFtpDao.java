package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourFtpEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-13 17:02:52
 */
public interface RecordHourFtpDao extends BaseDao<RecordHourFtpEntity> {
    List<RecordHourFtpEntity> queryFtpList(Map<String, Object> map);
    List<RecordHourFtpEntity> queryDayList(Map<String, Object> map);
}
