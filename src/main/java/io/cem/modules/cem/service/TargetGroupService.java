package io.cem.modules.cem.service;

import io.cem.modules.cem.entity.TargetGroupEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-10-12 17:12:45
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
