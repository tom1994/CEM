package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordHourPppoeEntity;

import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;
/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-12-12 15:11:34
 */
public interface RecordHourPppoeDao extends BaseDao<RecordHourPppoeEntity> {
    List<RecordHourPppoeEntity> queryPppoeList(Map<String, Object> map);
}
