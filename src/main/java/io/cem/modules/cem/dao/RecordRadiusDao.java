package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordRadiusEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordRadiusDao extends BaseDao<RecordRadiusEntity> {
    List<RecordRadiusEntity> queryRadiusTest(Map<String, Object> map);
}
