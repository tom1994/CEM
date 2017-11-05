package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TargetGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author ${author}
 * @email ${email}
 * @date 2017-11-05 20:39:28
 */
public interface TargetGroupService {
	
	TargetGroupEntity queryObject(Integer id);
	
	List<TargetGroupEntity> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(TargetGroupEntity targetGroup);
	
	void update(TargetGroupEntity targetGroup);
	
	void delete(Integer id);
	
	void deleteBatch(Integer[] ids);
}
