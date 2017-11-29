package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TargetEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.Map;
import java.util.List;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:39:28
 */
public interface TargetDao extends BaseDao<TargetEntity> {
    List<TargetEntity> infoBatch(Integer serviceId);
	
}
