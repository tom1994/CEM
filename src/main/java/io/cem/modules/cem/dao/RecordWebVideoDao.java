package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordWebVideoEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordWebVideoDao extends BaseDao<RecordWebVideoEntity> {
    List<RecordWebVideoEntity> queryWebVideoTest(Map<String, Object> map);
}
