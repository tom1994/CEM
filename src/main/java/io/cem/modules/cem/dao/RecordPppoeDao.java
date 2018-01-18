package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordPppoeEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordPppoeDao extends BaseDao<RecordPppoeEntity> {
    List<RecordPppoeEntity> queryPppoeTest(Map<String, Object> map);
}
