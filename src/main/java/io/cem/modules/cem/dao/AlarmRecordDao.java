package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.AlarmRecordEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:44
 */
public interface AlarmRecordDao extends BaseDao<AlarmRecordEntity> {
    List<AlarmRecordEntity> queryAlarmRecordList(Map<String, Object> map);
    void operate(Integer id);
}
