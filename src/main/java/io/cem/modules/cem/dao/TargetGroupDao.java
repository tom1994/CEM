package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TargetGroupEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:39:28
 */
public interface TargetGroupDao extends BaseDao<TargetGroupEntity> {
	List<TargetGroupEntity> queryTGList(Integer id);
}
