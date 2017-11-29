package io.cem.modules.cem.dao;

import io.cem.modules.cem.entity.TargetEntity;
import io.cem.modules.sys.dao.BaseDao;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:39:28
 */
public interface TargetDao extends BaseDao<TargetEntity> {
    List<TargetEntity> queryTargetList(Map<String,Object> map);
    List<TargetEntity> queryTarget(Integer id);
}
