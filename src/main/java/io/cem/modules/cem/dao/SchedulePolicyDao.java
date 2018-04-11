package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.SchedulePolicyEntity;
import io.cem.modules.sys.dao.BaseDao;

/**
 */
public interface SchedulePolicyDao extends BaseDao<SchedulePolicyEntity> {
    int queryExist(String spName);
}
