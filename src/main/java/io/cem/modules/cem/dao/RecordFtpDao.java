package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordDayFtpEntity;
import io.cem.modules.cem.entity.RecordFailEntity;
import io.cem.modules.cem.entity.RecordFtpEntity;
import io.cem.modules.cem.entity.RecordHourFtpEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordFtpDao extends BaseDao<RecordFtpEntity> {
    List<RecordFtpEntity> queryFtpTest(Map<String, Object> map);
    List<RecordHourFtpEntity> queryFtp(Map<String, Object> map);
    List<RecordDayFtpEntity> queryDay(Map<String, Object> map);
    List<RecordFtpEntity> queryFtpList(Map<String, Object> map);
    List<RecordFailEntity> queryFtpFail(Map<String, Object> map);
}
