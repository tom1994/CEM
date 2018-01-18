package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.AlarmRecordEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface AlarmRecordDao extends BaseDao<AlarmRecordEntity> {
    List<AlarmRecordEntity> queryAlarmRecordList(Map<String, Object> map);
    void operate(Integer id);
}
