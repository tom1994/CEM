package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordGameEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 *
 */
public interface RecordGameDao extends BaseDao<RecordGameEntity> {
    List<RecordGameEntity> queryGameTest(Map<String, Object> map);
}
