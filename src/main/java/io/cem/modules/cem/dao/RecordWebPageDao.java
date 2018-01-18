package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordWebPageEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordWebPageDao extends BaseDao<RecordWebPageEntity> {
    List<RecordWebPageEntity> queryWebPageTest(Map<String, Object> map);
}
