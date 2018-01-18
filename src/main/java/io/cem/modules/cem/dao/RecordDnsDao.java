package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordDnsEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordDnsDao extends BaseDao<RecordDnsEntity> {
    List<RecordDnsEntity> queryDnsTest(Map<String, Object> map);
}
