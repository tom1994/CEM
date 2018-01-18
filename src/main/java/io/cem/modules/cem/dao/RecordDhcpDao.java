package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.RecordDhcpEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 */
public interface RecordDhcpDao extends BaseDao<RecordDhcpEntity> {
    List<RecordDhcpEntity> queryDhcpTest(Map<String, Object> map);
}
