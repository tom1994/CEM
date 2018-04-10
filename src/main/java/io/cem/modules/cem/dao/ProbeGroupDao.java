package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.ProbeGroupEntity;
import io.cem.modules.sys.dao.BaseDao;

/**
 */
public interface ProbeGroupDao extends BaseDao<ProbeGroupEntity> {
    int queryExist(String pgName);
}
