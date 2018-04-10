package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TargetGroupEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;

/**
 */
public interface TargetGroupDao extends BaseDao<TargetGroupEntity> {
	List<TargetGroupEntity> queryTGList(Integer id);
	int queryExist(String tgName);
}
